package com.zlk.jdk.jdk8.thread.pool;

import com.zlk.jdk.jdk8.vo.User;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Description: CachedThreadPool(corePoolSize = 0，maximumPoolSize = Integer.MAX_VALUE，即线程数量几乎无限制)
 * @Author: ZhouLiKuan
 * @Date: 2021/02/18
 */
public class CachedThreadPoolTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 50000; i++) {
            //execute无结果返回
             executorService.execute(new MyTask());
        }
    }
}
