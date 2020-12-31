package com.zlk.jdk.jdk8.interfacedefaultmethod.stream;


import com.zlk.jdk.jdk8.vo.User;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Description: Stream 串行流
 * @Author: ZhouLiKuan
 * @Date: 2020/12/17 16:20
 */
public class StreamUtil {


    /**
     * 筛选与count()
     * @param list 集合
     * @param t 比较值
     * @param <T> 比较值类型
     * @return 满足的集合size
     */
    public static<T> long filter(List<String> list,T t){
        long count = list.stream().filter(s->s.equals(t)).count();
        return count;
    }


    /**
     * max_min
     * @param list 集合
     * @param <T> 比较值类型
     */
    public static<T> void maxOrMin(List<String> list){
        String max = list.stream().max((a, b) -> {
            if (a.compareTo(b)>0) {
                return 1;
            } else if (a.compareTo(b)<0){
                return -1;
            }else{
                return 0;
            }
        }).get();
        System.out.println("max:"+max);

        String min = list.stream().min((a, b) -> {
            if (a.compareTo(b)>0) {
                return 1;
            } else if (a.compareTo(b)<0){
                return -1;
            }else{
                return 0;
            }
        }).get();
        System.out.println("min:"+min);
    }

    /**
     * 结果转换（常见需要处理结果，或者PO转VO等）
     * @param list
     * @param <T>
     * @return
     */
    public static<T>  List<String> map(List<String> list){
        List<String> list2 = list.stream().map(f->{
            String str = f +"-修改";
            return str;
        }).collect(Collectors.toList());
        list2.forEach(System.out::println);

        return list2;
    }

    /**
     * 去重，只能针对对象，不能针对某个字段
     * @param list
     * @param <T>
     * @return
     */
    public static<T>  List<User> distinct(List<User> list){
        //需要返回需要使用map.且生效需要重写hashcode与equeals方法
        List<User> list2 = list.stream().distinct().map(f->{
            User user = new User();
            BeanUtils.copyProperties(f,user);
            return user;
        }).collect(Collectors.toList());
        return list2;
    }

    /**
     * 遍历集合,并编辑
     * @param list
     * @param <T>
     */
    public static<T>  void forEach(List<User> list){
        list.stream().forEach(s ->{
            s.setCity("中国");
            System.out.println(s);
            });
    }

    /**
     * 排序
     * @param list
     * @param <T>
     */
    public static<T>  void sorted(List<User> list){
        //默认升序
        //list.stream().sorted().forEach(System.out::println);
        //reversed()为降序
        list.stream().sorted(Comparator.comparing(User::getAge).reversed()).forEach(System.out::println);
    }

    /**
     * peek是个中间操作，它提供了一种对流中所有元素操作的方法，而不会把这个流消费掉（foreach会把流消费掉），然后你可以继续对流进行其他操作。
     * @param list
     * @param <T>
     */
    public static<T>  void peek(List<User> list){
        Stream<User>  st = list.stream().peek(s ->{
            s.setCity("中国");
        }).peek(s->{
            s.setCity("中国11");
        });
    }

    /**
     * 截取流中前面几个元素
     * @param list
     * @param <T>
     * @return
     */
    public static<T>  Stream<User> limit(List<User> list){
       return list.stream().limit(1);
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("1111");
        list.add("2222");
        list.add("2222");
        list.add("5555");
        list.add("5555");
       // long count = list.stream().filter(s->s.equals("2222")).count();
        //System.out.println(count);

       // map( list);

        List<User> userList = new ArrayList<>();
        User user = new User();
        user.setName("小张");
        user.setAge(20);
        userList.add(user);

        user = new User();
        user.setName("小张");
        user.setAge(25);
        userList.add(user);

        user = new User();
        user.setName("小李");
        user.setAge(25);
        userList.add(user);

        user = new User();
        user.setName("小李");
        user.setAge(25);
        userList.add(user);
        //distinct(userList).forEach(System.out::println);
        //forEach(userList);
        //sorted(userList);
        limit(userList).forEach(System.out::println);
    }

}
