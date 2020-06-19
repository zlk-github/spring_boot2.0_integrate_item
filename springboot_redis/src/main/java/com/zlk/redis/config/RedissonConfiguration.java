package com.zlk.redis.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfiguration {

        @Bean
        public RedissonClient getRedisson(){
            Config config = new Config();
            //单机模式  依次设置redis地址和密码.其余配置未加
            config.useSingleServer().
                    setAddress("redis://192.168.120.201:6379");
                   // .setPassword("");
            return Redisson.create(config);
        }


}
