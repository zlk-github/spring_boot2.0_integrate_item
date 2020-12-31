package com.zlk.jdk.jdk8.lambda;

import com.zlk.jdk.jdk8.interfacedefaultmethod.optional.OptionalUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description: lambda表达式
 * @Author: ZhouLiKuan
 * @Date: 2020/12/17 16:20
 */
public class lambdaUtil {
    public static void main(String[] args) {
        System.out.println("------------配合forEach实现循环遍历取值--------- ");
       //1.配合forEach实现循环遍历取值
        List<String> list = new ArrayList<>();
        list.add("测试");
        list.forEach(e->{
            System.out.println("e = " + e);
        });

        System.out.println("------------匿名内部类实现--------- ");
        //2.匿名内部类实现
        Runnable runnable = ()->{  System.out.println("执行run");};
        runnable.run();
        runnable.run();

        //3.对集合进行排序
        list.add("111");
        list.add("333");
        list.add("222");
        System.out.println("-----------升序---------- ");
        //升序
        list.sort((a, b) -> a.compareTo(b));
        list.forEach(e->{
            System.out.println("e = " + e);
        });

        System.out.println("-----------降序---------- ");
        //降序
        list.sort((a, b) -> b.compareTo(a));
        list.forEach(e->{
            System.out.println("e = " + e);
        });
    }
}
