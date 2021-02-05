package com.zlk.jdk.jdk8.thread.pool;

/**
 * @Description: 自定义一个线程
 * @Author: ZhouLiKuan
 * @Date: 2021/1/21
 */
public class MyTask implements Runnable{
    @Override
    public void run() {
        try {
            System.out.println("现在运行的是【 " + Thread.currentThread().getName() + "】任务");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
