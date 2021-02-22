
## 1 ThreadPoolExecutor线程池（推荐）

线程数与cpu数有关，线程数多，切换频繁耗资源（https://www.cnblogs.com/javalyy/p/10930330.html）。

最佳线程数目 = （（线程等待时间+线程CPU时间）/线程CPU时间 ）* CPU数目

    我们的服务器CPU核数为4核，一个任务线程cpu耗时为20ms，
    线程等待（网络IO、磁盘IO）耗时80ms，
    那最佳线程数目：( 80 + 20 )/20 * 4 = 20。也就是设置20个线程数最佳。
       
        （如IO时可以空闲出CPU，所以单核服务器也可以用多线程提升速度）
        1、网络请求----->网络IO
        2、解析请求----->CPU
        3、请求数据库----->网络IO
        4、MySQL查询数据----->磁盘IO
        5、MySQL返回数据----->网络IO
        6、数据处理----->CPU
        7、返回数据给用户----->网络IO

指标：

    QPS：每秒能够处理请求
    TPS：事务的数量
    并发数：系统同时处理的请求/事务的数量
    响应时间：就是平均处理一个请求/事务需要时长

    QPS/TPS = 并发数/响应时间

构造函数：

    public ThreadPoolExecutor(int corePoolSize, // 1
            int maximumPoolSize,  // 2
            long keepAliveTime,  // 3
            TimeUnit unit,  // 4
            BlockingQueue<Runnable> workQueue, // 5
            ThreadFactory threadFactory,  // 6
            RejectedExecutionHandler handler //7)

序号	|名称|	类型|	含义
---|---|---|---
1	|corePoolSize	|int	|核心线程池大小
2	|maximumPoolSize	|int	|最大线程池大小
3	|keepAliveTime	|long	|线程最大空闲时间
4	|unit	|TimeUnit	|时间单位
5	|workQueue	|BlockingQueue<Runnable>	|线程等待队列
6	|threadFactory	|ThreadFactory	|线程创建工厂
7	|handler	|RejectedExecutionHandler	|拒绝策略

下面几种为JDK给我们预定义的几种线程池(通过ThreadPoolExecutor不同的构造参数实现)：

### 1.预定义线程池

#### 1.1 FixedThreadPool （核心线程数与最大线程池数相等）

    创建重用固定数量线程的线程池，不能随时新建线程
  
    当所有线程都处于活动状态时，如果提交了其他任务，
    他们将在队列中等待一个线程可用
  
    线程会一直存在，直到调用shutdown

    public static ExecutorService newFixedThreadPool(int nThreads) {
        return new ThreadPoolExecutor(nThreads, nThreads,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>());
    }

- corePoolSize核心线程数与maximumPoolSize最大线程池数相同，是一个固定大小的线程池（且全为核心线程）：
- 优点：
  
   1. keepAliveTime=0对核心线程无效，FixedThreadPool全部为核心线程。
      
- 缺点：
      
    1. workQueue 为LinkedBlockingQueue（无界阻塞队列），队列最大值为Integer.MAX_VALUE。如果任务提交速度持续大余任务处理速度，会造成队列大量阻塞。因为队列很大，很有可能在拒绝策略前，内存溢出；
    
    2. FixedThreadPool的任务执行是无序的；
  
适用场景：可用于Web服务瞬时削峰，但需注意长时间持续高峰情况造成的队列阻塞。

#### 1.2.CachedThreadPool（不限制线程数，短任务）
    
    重用之前的线程
    适合执行许多短期异步任务的程序。
    调用 execute() 将重用以前构造的线程
    如果没有可用的线程，则创建一个新线程并添加到池中
    默认为60s未使用就被终止和移除
    长期闲置的池将会不消耗任何资源

    public static ExecutorService newCachedThreadPool() {
        return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
        60L, TimeUnit.SECONDS,
        new SynchronousQueue<Runnable>());
    }

- corePoolSize = 0，maximumPoolSize = Integer.MAX_VALUE，即线程数量几乎无限制；

- keepAliveTime = 60s，线程空闲60s后自动结束。

- workQueue 为 SynchronousQueue 同步队列，这个队列类似于一个接力棒，入队出队必须同时传递，
    因为CachedThreadPool线程创建无限制，不会有队列等待，所以使用SynchronousQueue；

适用场景：快速处理大量耗时较短的任务，如Netty的NIO接受请求时，可使用CachedThreadPool。

#### 1.3 SingleThreadExecutor（单线程）

        在任何情况下都不会有超过一个任务处于活动状态
        与newFixedThreadPool(1)不同是不能重新配置加入线程，使用FinalizableDelegatedExecutorService进行包装
        能保证执行顺序，先提交的先执行。
        当线程执行中出现异常，去创建一个新的线程替换之

        源码：
        public static ExecutorService newSingleThreadExecutor() {
            return new FinalizableDelegatedExecutorService
            (new ThreadPoolExecutor(1, 1,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>()));
        }

适用场景：适用于需要保证顺序执行各个任务，并且在任意时间点，不会有多个线程是活动的场景。

#### 1.4 newScheduledThreadPool（延时线程）

    设定延迟时间，定期执行
    空闲线程会进行保留

    public static ScheduledExecutorService newScheduledThreadPool(int corePoolSize) {
        return new ScheduledThreadPoolExecutor(corePoolSize);
    }

    public ScheduledThreadPoolExecutor(int corePoolSize) {
        super(corePoolSize, Integer.MAX_VALUE, 0, NANOSECONDS,
        new DelayedWorkQueue());
    }

#### 1.5 newWorkStealingPool

    获取当前可用的线程数量进行创建作为并行级别
    使用ForkJoinPool
    源码：
    
    public static ExecutorService newWorkStealingPool() {
        return new ForkJoinPool
        (Runtime.getRuntime().availableProcessors(),
        ForkJoinPool.defaultForkJoinWorkerThreadFactory,
        null, true);
    }
    通过源码可以看出底层调用的是ForkJoinPool线程池
    
    ForkJoinPool
    public ForkJoinPool(int parallelism,
        ForkJoinWorkerThreadFactory factory,
        UncaughtExceptionHandler handler,
        boolean asyncMode) {
            this(checkParallelism(parallelism),
            checkFactory(factory),
            handler,
            asyncMode ? FIFO_QUEUE : LIFO_QUEUE,
            "ForkJoinPool-" + nextPoolId() + "-worker-");
            checkPermission();
    }

使用一个无限队列来保存需要执行的任务，可以传入线程的数量，不传入，则默认使用当前计算机中可用的cpu数量，使用分治法来解决问题，使用fork()和join()来进行调用

### 五种线程池的适应场景

newCachedThreadPool：用来创建一个可以无限扩大的线程池，适用于服务器负载较轻，执行很多短期异步任务。

newFixedThreadPool：创建一个固定大小的线程池，因为采用无界的阻塞队列，所以实际线程数量永远不会变化，适用于可以预测线程数量的业务中，或者服务器负载较重，对当前线程数量进行限制。

newSingleThreadExecutor：创建一个单线程的线程池，适用于需要保证顺序执行各个任务，并且在任意时间点，不会有多个线程是活动的场景。

newScheduledThreadPool：可以延时启动，定时启动的线程池，适用于需要多个后台线程执行周期任务的场景。

newWorkStealingPool：创建一个拥有多个任务队列的线程池，可以减少连接数，创建当前可用cpu数量的线程来并行执行，适用于大耗时的操作，可以并行来执行
    
    submit()，提交一个线程任务，可以接受回调函数的返回值吗，适用于需要处理返回着或者异常的业务场景
    execute()，执行一个任务，没有返回值
    shutdown()，表示不再接受新任务，但不会强行终止已经提交或者正在执行中的任务
    shutdownNow()，对于尚未执行的任务全部取消，正在执行的任务全部发出interrupt()，停止执行

### 参考：

https://www.jianshu.com/p/f030aa5d7a28

https://blog.csdn.net/qq_17045385/article/details/87883101

https://blog.csdn.net/sinat_15946141/article/details/107951917

https://www.cnblogs.com/cdf-opensource-007/p/8769777.html