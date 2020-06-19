package com.zlk.redis.dao;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 模拟数据库返回
 */
@Component
public class UserDao {
    /**
     * 查询用户（测试缓存击穿，数据库存在，缓存失效）
     * @return 用户
     */
    public User getUserById(){
        System.out.println("---------查询数据库--------");
        return  new User(1, " name"+1,20);
    }

    /**
     * 查询所有(用于模拟缓存穿透，缓存与数据库都查不到对应值情况)
     * @return 用户列表
     */
    public User getUserById1(){
        return null;
    }


}
