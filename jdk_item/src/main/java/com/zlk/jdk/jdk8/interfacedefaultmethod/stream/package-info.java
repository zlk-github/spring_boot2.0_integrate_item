package com.zlk.jdk.jdk8.interfacedefaultmethod.stream;

/**
Stream（流）是一个来自数据源的元素队列并支持聚合操作

    元素是特定类型的对象，形成一个队列。 Java中的Stream并不会存储元素，而是按需计算。
    数据源 流的来源。 可以是集合，数组，I/O channel， 产生器generator 等。
    聚合操作 类似SQL语句一样的操作， 比如filter, map, reduce, find, match, sorted等。

 Stream分惰性求值与及早求值：惰性求值不会产生新的集合，及早求值会产生新的值（如调count(),collect()等）

并行与串行

Stream（串行）接口中包含许多对流操作的方法，这些方法分别为：
        *filter()：对流的元素过滤
        *map()：将流的元素映射成另一个类型
        *forEach()：对流中的每个元素执行某个操作
        *peek()：与forEach()方法效果类似，不同的是，该方法会返回一个新的流（可以多次操作），而forEach()无返回
        *sorted()：对流的元素排序
        *limit()：截取流中前面几个元素
        *collect()：对流的汇总操作，比如输出成List集合
        *toArray()：将流转换为数组
        *reduce()：对流中的元素归约操作，将每个元素合起来形成一个新的值
        *anyMatch()：匹配流中的元素（匹配上任意一个返回true），类似的操作还有allMatch()匹配所有和noneMatch()匹配一个方法.
        distinct()：去除流中重复的元素(只能针对整个实体的hashcode,作用不大)
        skip()：跳过流中前面几个元素
        findFirst()：查找第一个元素，类似的还有findAny()方法
        max()：求最大值
        min()：求最小值
        count()：求总数

parallelStream（并行）

     利用多核处理器的优势快速处理集合（集合的数据会分成多个段，由多个线程处理）。
     其底层使用Fork/Join框架实现。简单理解就是多线程异步任务的一种实现。会存在线程问题。
     适合数据量大，不依赖顺序，阻塞性小的场景。


 //针对previewUrl去重后返回list
 list = list.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() ->
 new TreeSet<>(Comparator.comparing(f -> f.getPreviewUrl()))), ArrayList::new));


 */
