package com.zlk.jdk.jdk8.thread.pool;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description: WorkStealingPool(获取当前可用的线程数量进行创建作为并行级别)
 * @Author: ZhouLiKuan
 * @Date: 2021/02/18
 */
public class WorkStealingPoolTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService executorService = Executors.newWorkStealingPool();
        for (int i = 0; i < 50; i++) {
            //execute无结果返回
            executorService.execute(new MyTask());
        }

    }
}
