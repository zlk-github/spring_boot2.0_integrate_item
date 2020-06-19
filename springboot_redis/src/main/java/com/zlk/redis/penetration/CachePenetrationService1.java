package com.zlk.redis.penetration;

import com.zlk.redis.config.Constant;
import com.zlk.redis.dao.User;
import com.zlk.redis.dao.UserDao;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 缓存穿透（数据库与缓存中都查不到）
 * 方式2：使用布隆过滤器的方式解决。牺牲准确率换取效率。击穿也放在这里
 */
@Service
public class CachePenetrationService1 {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedissonClient redisson;
    @Autowired
    private BloomFilterHelper bloomFilterHelper;
    @Autowired
    private  RedisBloomFilter redisBloomFilter;

    /**
     * 使用返布隆过滤器解决缓存穿透
     * 查询所有（测试缓存击穿，数据库存在，缓存失效）
     * @return 用户列表
     */
    public User getUserList(String id){
        //布隆过滤器判断key不存在直接返回（缓存穿透）
        boolean bloomFilter = redisBloomFilter.includeByBloomFilter(bloomFilterHelper,  Constant.BLOOM_KEY, id);
        if(!bloomFilter){
            //key不存在，也没有数据库对应数据。缓存穿透，直接返回。
            System.out.println("缓存穿透,直接返回");
            return null;
        }
        //key设置为user_list,测试代码不考虑分页
        User user = (User)redisTemplate.opsForValue().get(id);
        if (user!=null){
            System.out.println("1--缓存中有对应值");
            return user;
        }

        //分布式锁，redisson保证缓存的分布式锁不过期。（解决缓存击穿）
        RLock lock = redisson.getLock(id);
        try{
            //阻塞，设置时间。保证redis中可以解锁。
            lock.lock(30,TimeUnit.SECONDS);
            //再查询一次缓存，当第一次入缓存成功后，第一个线程以缓存。不再查询数据库。
            user = (User)redisTemplate.opsForValue().get(id);
            if (user!=null){
                System.out.println("2--缓存中有对应值");
                return user;
            }
            //缓存中不存在，查寻数据库
            user = userDao.getUserById1();
            System.out.println("数据库中有对应值，缓存中过期，查询数据库");
            //设置时间30分钟失效，将数据写到redis。
            redisTemplate.opsForValue().set(id,user,20, TimeUnit.SECONDS);
        }finally {
            //保证解锁成功
            lock.unlock();
        }
        return user;
    }



// 已过时，会有容错误判（和数组长度与hash个数有关，每个hash定位到的位置标记为1）。hash会出现碰撞，内存有共享。就是牺牲准确率换取速度与内存。
// private static BloomBuilder<Integer> blooFilter = BloomBuilder.create(Funnels.integerFunnel(),10000000,0.01);



}
