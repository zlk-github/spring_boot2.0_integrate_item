package com.zlk.redis.redisson;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 测试分布式锁
 */
@RestController
@RequestMapping("/redisson")
public class RedissonController {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private RedissonClient redisson;


    /**
     *  synchronized非单系统会出现问题
     */
    @RequestMapping("/lock")
    public String lock(){
        //不加synchronized，并发大时stock会出现线程问题。数量不对。
        // 但是synchronized针对单服务有效。对分布式系统由于JVM有多个。此时将不能使用
        synchronized (this){
            //库存
            int stock = Integer.parseInt(redisTemplate.opsForValue().get("stock"));
            if(stock>0){
                redisTemplate.opsForValue().set("stock",--stock+"");
                System.out.println("扣除成功，剩余库存："+stock);
            }else{
                System.out.println("库存不足！");
            }
        }
        return null;
    }

    /**
     *  分布式锁
     */
    @RequestMapping("/lock1")
    public String lock1(){
        String lockKey = "product_001";
        String uuid = UUID.randomUUID().toString();
        try{
            //setnx 不存在能添加成功返回true。已存在key不能修改，返回false。
            // 不设置失效时间可能导致锁不能释放（如刚好重启而锁没有释放）.
            // 但是可能方法没执行完导致锁先释放出问题。极端情况会刚加锁就被的线释放掉。加一个唯一标识UUID,自己的允许删除
            Boolean result = redisTemplate.opsForValue().setIfAbsent(lockKey, uuid,10, TimeUnit.SECONDS);
            if (!result){
                //锁存在，别的线程在此返回。等锁解开才能往下执行
                //为了使锁不在方法没结束失效。可以定时器延迟时间 。但是很复杂。不推荐
                return "error";
            }
            //查询库存
            int stock = Integer.parseInt(redisTemplate.opsForValue().get("stock"));
            if(stock>0){
                redisTemplate.opsForValue().set("stock",--stock+"");
                System.out.println("扣除成功，剩余库存："+stock);
            }else{
                System.out.println("库存不足！");
            }
        }finally {
            //当前线程的锁只允许当前删除
            if(uuid.equals(redisTemplate.opsForValue().get(lockKey))){
                //业务完成或者 报异常需要释放分布式锁。
                redisTemplate.delete(lockKey);
            }
        }
        return null;
    }

    /**
     *  分布式锁redisson
     *  改进可以使用分段锁
     */
    @RequestMapping("/lock2")
    public String lock2(){
        String lockKey = "product_001";
        //String uuid = UUID.randomUUID().toString();
        //redisson 每过10秒（1/3）会重置锁时间
        RLock lock = redisson.getLock(lockKey);
        try{
         /*   //setnx 不存在能添加成功返回true。已存在key不能修改，返回false。
            // 不设置失效时间可能导致锁不能释放（如刚好重启而锁没有释放）.
            // 但是可能方法没执行完导致锁先释放出问题。极端情况会刚加锁就被的线释放掉。加一个唯一标识UUID,自己的允许删除
            Boolean result = redisTemplate.opsForValue().setIfAbsent(lockKey, uuid,10, TimeUnit.SECONDS);
            if (!result){
                //锁存在，别的线程在此返回。等锁解开才能往下执行
                //为了使锁不在方法没结束失效。可以定时器延迟时间 。但是很复杂。不推荐
                return "error";
            }*/


            //除当前加锁成功的锁，别的锁会阻塞。会导致性能问题
            //可以考虑使用库存按一定量分成多段实现。（分段锁-hashmap形式）。
            // 主从来不及复制会出现一些问题（可以考虑zookpeer）
            lock.lock(30,TimeUnit.SECONDS);
            //查询库存
            int stock = Integer.parseInt(redisTemplate.opsForValue().get("stock"));
            if(stock>0){
                redisTemplate.opsForValue().set("stock",--stock+"");
                System.out.println("扣除成功，剩余库存："+stock);
            }else{
                System.out.println("库存不足！");
            }
        }finally {
           /* //当前线程的锁只允许当前删除
            if(uuid.equals(redisTemplate.opsForValue().get(lockKey)){
                //业务完成或者 报异常需要释放分布式锁。
                redisTemplate.delete(lockKey);
            }*/
            //业务完成或者 报异常需要释放分布式锁。
            //lock.unlock();
        }
        return null;
    }



}
