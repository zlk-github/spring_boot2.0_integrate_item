
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

    QPS/TPS：每秒能够处理请求/事务的数量
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

FixedThreadPool （核心线程数与最大线程池数相等）

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



### 五种线程池的适应场景

newCachedThreadPool：用来创建一个可以无限扩大的线程池，适用于服务器负载较轻，执行很多短期异步任务。

newFixedThreadPool：创建一个固定大小的线程池，因为采用无界的阻塞队列，所以实际线程数量永远不会变化，适用于可以预测线程数量的业务中，或者服务器负载较重，对当前线程数量进行限制。

newSingleThreadExecutor：创建一个单线程的线程池，适用于需要保证顺序执行各个任务，并且在任意时间点，不会有多个线程是活动的场景。

newScheduledThreadPool：可以延时启动，定时启动的线程池，适用于需要多个后台线程执行周期任务的场景。

newWorkStealingPool：创建一个拥有多个任务队列的线程池，可以减少连接数，创建当前可用cpu数量的线程来并行执行，适用于大耗时的操作，可以并行来执行

### 参看：

https://www.jianshu.com/p/f030aa5d7a28

https://blog.csdn.net/qq_17045385/article/details/87883101