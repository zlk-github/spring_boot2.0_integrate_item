package com.zlk.jdk.jdk8.thread.pool;

import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description: ThreadPoolExecutor(线程池原生，推荐)
 * @Author: ZhouLiKuan
 * @Date: 2021/01/19
 */
public class ThreadPoolExecutorTest {
    private static final int CORE_SIZE = 1;

    /**线程名称*/
    private static final String THREAD_NAME_PREFIX = "thread-";


    public static void main(String[] args) {

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(CORE_SIZE, CORE_SIZE*3,60,
                TimeUnit.SECONDS,new ArrayBlockingQueue<>(4), new CustomizableThreadFactory(THREAD_NAME_PREFIX));
        threadPoolExecutor.execute(new MyTask());
        threadPoolExecutor.execute(new MyTask());
        threadPoolExecutor.execute(new MyTask());
        threadPoolExecutor.execute(new MyTask());
        threadPoolExecutor.execute(new MyTask());
    }

   /* ThreadPoolExecutor(int corePoolSize,
                       int maximumPoolSize,
                       long keepAliveTime,
                       TimeUnit unit,
                       BlockingQueue<Runnable> workQueue,
                       ThreadFactory threadFactory) {
        this(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
                threadFactory, defaultHandler);

                1	|corePoolSize	|int	|核心线程池大小
                2	|maximumPoolSize	|int	|最大线程池大小
                3	|keepAliveTime	|long	|线程最大空闲时间
                4	|unit	|TimeUnit	|时间单位
                5	|workQueue	|BlockingQueue<Runnable>	|线程等待队列
                6	|threadFactory	|ThreadFactory	|线程创建工厂
                7	|handler	|RejectedExecutionHandler	|拒绝策略

    }*/
}
