package com.zlk.jdk.jdk8.thread.pool;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description: SingleThreadExecutor(单线程池)
 * @Author: ZhouLiKuan
 * @Date: 2021/02/18
 */
public class SingleThreadExecutorTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 50000; i++) {
            //execute无结果返回
            executorService.execute(new MyTask());
        }
    }
}
