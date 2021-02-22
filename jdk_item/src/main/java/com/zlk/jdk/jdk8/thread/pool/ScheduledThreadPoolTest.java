package com.zlk.jdk.jdk8.thread.pool;

import java.util.concurrent.*;

/**
 * @Description: ScheduledThreadPool(延时线程)
 * @Author: ZhouLiKuan
 * @Date: 2021/02/18
 */
public class ScheduledThreadPoolTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(10);
        for (int i = 0; i < 50000; i++) {
            //execute无结果返回
           executorService.schedule(new MyTask(),60, TimeUnit.SECONDS);
        }
    }
}
