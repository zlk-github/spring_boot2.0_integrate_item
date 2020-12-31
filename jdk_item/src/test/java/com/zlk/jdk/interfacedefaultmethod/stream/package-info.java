package com.zlk.jdk.interfacedefaultmethod.stream;

/*
并行与串行
Stream接口中包含许多对流操作的方法，这些方法分别为：
        filter()：对流的元素过滤
        map()：将流的元素映射成另一个类型
        distinct()：去除流中重复的元素
        sorted()：对流的元素排序
        forEach()：对流中的每个元素执行某个操作
        peek()：与forEach()方法效果类似，不同的是，该方法会返回一个新的流，而forEach()无返回
        limit()：截取流中前面几个元素
        skip()：跳过流中前面几个元素
        toArray()：将流转换为数组
        reduce()：对流中的元素归约操作，将每个元素合起来形成一个新的值
        collect()：对流的汇总操作，比如输出成List集合
        anyMatch()：匹配流中的元素，类似的操作还有allMatch()和noneMatch()方法
        findFirst()：查找第一个元素，类似的还有findAny()方法
        max()：求最大值
        min()：求最小值
        count()：求总数*/
