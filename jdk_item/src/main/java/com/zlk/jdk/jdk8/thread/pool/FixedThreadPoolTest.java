package com.zlk.jdk.jdk8.thread.pool;

import com.zlk.jdk.jdk8.vo.User;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Description: FixedThreadPool(核心线程与最大线程相等)
 * @Author: ZhouLiKuan
 * @Date: 2021/01/19
 */
public class FixedThreadPoolTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        for (int i = 0; i < 5; i++) {
            //execute无结果返回
           // executorService.execute(new MyTask());
        }

        for (long i = 0L; i < Long.MAX_VALUE; i++) {
            //submit可以返回执行结果
            System.out.println("任务开始 = " + i);
            Future<?> future = executorService.submit(new MyCallTask<List<User>>());
            Object o = future.get();
            System.out.println("future = " + o);
            System.out.println("任务结束 = " + i);
        }


    }
}
