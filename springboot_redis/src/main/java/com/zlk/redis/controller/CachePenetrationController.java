package com.zlk.redis.controller;

import com.zlk.redis.dao.User;
import com.zlk.redis.penetration.CachePenetrationService;
import com.zlk.redis.penetration.CachePenetrationService1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *缓存穿透（数据库与缓存中都查不到）
 */
@RestController
@RequestMapping("/penetration")
public class CachePenetrationController {
    @Autowired
    private CachePenetrationService cachePenetrationService;
    @Autowired
    private CachePenetrationService1 cachePenetrationService1;

 /**
     * 查询所有（测试缓存击穿，数据库存在，缓存失效）
     * @return 用户列表
     */
    @RequestMapping("/getUserList")
    public User getUserList(String key){
        return cachePenetrationService.getUserList(key);
    }

    /**
     * 查询所有（测试缓存击穿，数据库存在，缓存失效）
     * @return 用户列表
     */
    @RequestMapping("/getUserList11")
    public User getUserList1(String key){
        return cachePenetrationService1.getUserList(key);
    }


}
