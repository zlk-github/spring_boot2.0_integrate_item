package com.zlk.redis;


import com.google.common.hash.Funnel;
import com.zlk.redis.config.Constant;
import com.zlk.redis.penetration.BloomFilterHelper;
import com.zlk.redis.penetration.RedisBloomFilter;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class RedisApplicationTests {
    @Autowired
    private BloomFilterHelper bloomFilterHelper;
    @Autowired
    private RedisBloomFilter redisBloomFilter;

    @Test
    public void testSender() {
        //布隆表达式入初始redis key
        for (int i = 1001; i <= 9999; i++) {
            redisBloomFilter.addByBloomFilter(bloomFilterHelper, Constant.BLOOM_KEY,i);
        }

        int num = 0;
        for (int i = 2000; i <= 5000; i++) {
            if(!redisBloomFilter.includeByBloomFilter(bloomFilterHelper,Constant.BLOOM_KEY,i)){
                //误判
                num++;
            }
        }

        System.out.println("误判数： = [" + num + "]");
    }




}
