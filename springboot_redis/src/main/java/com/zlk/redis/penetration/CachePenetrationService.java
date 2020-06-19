package com.zlk.redis.penetration;

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
 * 方式1：使用返回空值做缓存（时间短，5分钟左右。不管是数据不存在还是系统服务出问题）的方式解决。击穿也放在这里
 */
@Service
public class CachePenetrationService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedissonClient redisson;

    /**
     * 使用返回空对象解决缓存穿透
     * 查询所有（测试缓存击穿，数据库存在，缓存失效）
     * @return 用户列表
     */
    public User getUserList(String id){

        //key设置为user_list,测试代码不考虑分页
        User user = (User)redisTemplate.opsForValue().get(id);
        //当缓存与数据库都查不到数据时，第一次查询会走if(user ==null)，
        // 然后缓存一个短期的key:null到缓存，再次查询时将user!=null
        if (user!=null){
           if (user instanceof User){
                //但是此时增加，删除，修改时。key需要做相关调整。如果有真正的值对应key
                System.out.println("数据库与缓存未命中时，缓存的null");
            }else{
                System.out.println("缓存中有对应值");
            }
            return user;
        }else{

            //分布式锁，redisson保证缓存的分布式锁不过期。（解决缓存击穿）
            RLock lock = redisson.getLock(id);
            try{
                //阻塞，设置时间。保证redis中可以解锁。
                lock.lock(30,TimeUnit.SECONDS);
                //再查询一次缓存，当第一次入缓存成功后，第一个线程以缓存。不再查询数据库。
                user = (User)redisTemplate.opsForValue().get(id);
                if (user!=null){
                    if (user instanceof User){
                        //但是此时增加，删除，修改时。key需要做相关调整。如果有真正的值对应key
                        System.out.println("2--数据库与缓存未命中时，缓存的null");
                    }else{
                        System.out.println("2--缓存中有对应值");
                    }
                    return user;
                }

                //缓存中不存在，查寻数据库
                user = userDao.getUserById1();
                //数据库中不存在。不存在，将空值缓存，
                if(user ==null){
                    System.out.println("****数据库和缓存中都无有对应值，被缓存穿透");
                    //保存一个空对象（不是null），设置时间5分钟。短时间失效。为了防止数据穿透.但是会导致大量空数据。
                    //最好再建一个对象
                    redisTemplate.opsForValue().set(id,new User(),5, TimeUnit.MINUTES);
                }else{
                    System.out.println("数据库中有对应值，缓存中过期");
                    //设置时间30分钟失效，将数据写到redis。
                    redisTemplate.opsForValue().set(id,user,20, TimeUnit.SECONDS);
                }
            }finally {
                //保证解锁成功
                lock.unlock();
            }
        }
        return user;
    }


}
