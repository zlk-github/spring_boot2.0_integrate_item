package com.zlk.redis.penetration;

import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisBloomFilter<T> {

    @Autowired
    private  RedisTemplate redisTemplate;

    /**
     * 根据给定的布隆过滤器添加值
     */
    public  <T>  void addByBloomFilter(BloomFilterHelper<T> bloomFilterHelper, String key, T value) {
        Preconditions.checkArgument(bloomFilterHelper != null, "bloomFilterHelper不能为空");
        //具体hash
        int[] offset = bloomFilterHelper.murmurHashOffset(value);
        for (int i : offset) {
            //数组中（key）对应hash位置（i）标记1（setBit）
            redisTemplate.opsForValue().setBit(key, i, true);
        }
    }

    /**
     * 根据给定的布隆过滤器判断值是否存在
     */
    public   <T>  boolean includeByBloomFilter(BloomFilterHelper<T> bloomFilterHelper, String key, T value) {
        Preconditions.checkArgument(bloomFilterHelper != null, "bloomFilterHelper不能为空");
        //具体hash
        int[] offset = bloomFilterHelper.murmurHashOffset(value);
        for (int i : offset) {
            //查看各个hash值（i）是否在redis（key中,就是个位数组）中有命中值(setbit命中位置为1则存在，为0不存在)
            if (!redisTemplate.opsForValue().getBit(key, i)) {
                return false;
            }
        }

        return true;
    }



}
