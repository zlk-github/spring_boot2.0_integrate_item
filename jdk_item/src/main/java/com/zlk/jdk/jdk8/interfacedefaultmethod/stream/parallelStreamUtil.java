package com.zlk.jdk.jdk8.interfacedefaultmethod.stream;

import com.zlk.jdk.jdk8.vo.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: parallelStream 并行流
 * @Author: ZhouLiKuan
 * @Date: 2020/12/17 16:20
 */
public class parallelStreamUtil {

    /**
     * 遍历集合,并编辑
     * @param list
     * @param <T>
     */
    public static<T>  void forEach(List<Integer> list){
        list.parallelStream().forEach(System.out::println);
        //输入1,2,3,4,5,6。输出无序（多线程分段并行）
       /*
        4
        5
        1
        6
        2
        3*/
    }

    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        forEach(list);

    }
}
