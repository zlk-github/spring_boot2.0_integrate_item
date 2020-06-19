# Spring Boot 常用框架整合

## springboot_redis    springboot整合redis

    redis缓存穿透：数据库与缓存中都不存在，黑客大量访问打到数据库；（布隆过滤器/返回空对象）
    redis缓存击穿：数据库中存在对应值，redi缓存过期，大量请求访问打到数据库；（分布式锁Redisson）
    redis缓存雪崩：缓存大面积失效，或者重启消耗大量资源。（缓存时间设置随机，启动加到队列等）

## rabbit_mq    springboot整合rabbitMq
    
    发布-订阅（direct）：路由键完全匹配，路由键绑定队列与交换器。
    主题（topic）：也是通过路由键（模糊匹配，*一个，#一个或者多个）规则绑定交换器与队列。
    广播（fanout）：消息将群发到当前交换器下绑定的所有队列上。该交换器队列与交换器不通过路由器绑定。 
    headers：通过headers来决定把消息发给哪些queue，用的比较少。