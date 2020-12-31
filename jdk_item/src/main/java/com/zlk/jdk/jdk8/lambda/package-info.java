package com.zlk.jdk.jdk8.lambda;
/*
lambda表达式（{e->实现方法块}），lambda表达式依赖于上下文环境，常见形式
    表达式不带参数 ()-> System.out.println("");
    表达式带参数一个参数 (e)-> System.out.println(e);,也可以写为 e-> System.out.println(e)；
    表达式多参数(x,y)->x+y;

作用：

    配合forEach实现循环遍历取值
    替代匿名内部类实现
    配合sort集合排序

 */