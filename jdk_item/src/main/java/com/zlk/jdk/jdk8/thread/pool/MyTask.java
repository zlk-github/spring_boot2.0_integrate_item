package com.zlk.jdk.jdk8.thread.pool;

import lombok.SneakyThrows;

/**
 * @Description: 自定义一个线程
 * @Author: ZhouLiKuan
 * @Date: 2021/1/21
 */
public class MyTask implements Runnable{

    @SneakyThrows
    @Override
    public void run() {
        Thread.sleep(3 * 1000);
        System.out.println("现在运行的是【 " + Thread.currentThread().getName() + "】任务");
    }
}
