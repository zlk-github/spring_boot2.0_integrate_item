package com.zlk.jdk.jdk8.completablefuture;

import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.concurrent.*;

public class MyThreadPoolExecutor {

    private static final int CORE_SIZE = 10;

    /**线程名称*/
    private final String THREAD_NAME_PREFIX = "thread-";

    ThreadPoolExecutor  THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(CORE_SIZE, CORE_SIZE*3,60,
            TimeUnit.SECONDS,new ArrayBlockingQueue<>(2000), new CustomizableThreadFactory(THREAD_NAME_PREFIX));

   /* ThreadPoolExecutor(int corePoolSize,
                       int maximumPoolSize,
                       long keepAliveTime,
                       TimeUnit unit,
                       BlockingQueue<Runnable> workQueue,
                       ThreadFactory threadFactory) {
        this(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
                threadFactory, defaultHandler);
    }*/

}
