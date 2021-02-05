package com.zlk.jdk.jdk8.thread.pool;


import com.zlk.jdk.jdk8.vo.User;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 自定义有返回值线程
 * @Author: ZhouLiKuan
 * @Date: 2021/1/21
 */
public class MyCallTask<T> implements Callable<T> {

    @Override
    public T call() {
        T t = null;
        try {
            Byte[] bytes = new Byte[1024 * 1000 * 500];
            t = get(Thread.currentThread().getName());
            //TimeUnit.MINUTES.sleep(1);
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return t;
    }

    public T get(String taskName) {
        ArrayList<User> arrayList = new ArrayList<>();
        User user ;
        for (int i = 0; i < 100; i++) {
            user = new User();
            user.setAge(i);
            user.setName(taskName);
            arrayList.add(user);
        }
        return (T)arrayList;
    }
}
