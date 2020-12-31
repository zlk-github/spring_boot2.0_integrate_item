package com.zlk.jdk.interfacedefaultmethod.optional;


import com.zlk.jdk.jdk8.interfacedefaultmethod.optional.OptionalUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * @Description: Optional 判空
 * @Author: ZhouLiKuan
 * @Date: 2020/12/17 16:20
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class OptionalTest {

    @Test
    public void testEmpty(){
        Optional<Object> empty = OptionalUtil.empty();
        System.out.println(empty); //Optional.empty    注:（内容为null）
    }

    @Test
    public void testOfNullable(){
        System.out.println("----------Optional.ofNullable(t)-----------");
        //2 Optional.ofNullable(t)
        List<String> list = new ArrayList<>();
        Optional<List<String>> optional = OptionalUtil.ofNullable(list);
        System.out.println(optional); //Optional[[]]

        list.add("测试");
        optional = OptionalUtil.ofNullable(list);
        System.out.println(optional); //Optional[[测试]]
        System.out.println("--------------------------------------");
    }

    @Test
    public void testOf(){
        System.out.println("----------Optional.of(t)-----------");
        //Optional<Object> o = Optional.of(null);
        //System.out.println(o); //java.lang.NullPointerException
        List<String> list = new ArrayList<>();
        Optional<Object> o = Optional.of(list);
        o = OptionalUtil.of(list);
        System.out.println(o);
    }



    @Test
    public void testIfPresent(){
        System.out.println("----------Optional.ifPresent(t)-----------");
        List<String> list = new ArrayList<>();
        //不能判断集合内是否有值，size()==0也会执行ifPresent
        OptionalUtil.ifPresent(list);//ifPresent判断存在值，执行结果：[]

        list = null;
        //null时，ifPresent不执行
        OptionalUtil.ifPresent(list);//无结果输出，可以判断空

        list = new ArrayList<>();
        list.add("测试");
        OptionalUtil.ifPresent(list);//ifPresent判断存在值，执行结果：[测试]

    }

    @Test
    public void testGet(){
        System.out.println("----------Optional.get(t)-----------");
        List<String> list = new ArrayList<>();
        list.add("测试");
        //存在返回Optional内对象值
        System.out.println(OptionalUtil.get(list));//[测试]

        list = null;
        //null时，NoSuchElementException
        System.out.println(OptionalUtil.get(list));


    }

    @Test
    public void testEquals(){
        System.out.println("----------Optional.equals(t)-----------");
        List<String> list = new ArrayList<>();
        list.add("测试");
        List<String> list2 = new ArrayList<>();
        list2.add("测试");
        //存在返回Optional内对象值
        System.out.println(OptionalUtil.equals(list,list2));//true

        list = null;
        //null时
        System.out.println(OptionalUtil.equals(list,list2));//false

    }


    @Test
    public void testOrElse(){
        System.out.println("----------Optional.orElse(t)-----------");
        List<String> list = new ArrayList<>();
        System.out.println(OptionalUtil.orElse(list));//[]

        list = new ArrayList<>();
        list.add("测试");
        System.out.println(OptionalUtil.orElse(list));//[测试]

        list = null;
        //null时
        System.out.println(OptionalUtil.orElse(list));//null

    }

    @Test
    public void testFilter(){
        System.out.println("----------Optional.orElse(t)-----------");
        ArrayList<String> list = new ArrayList<>();
        OptionalUtil.filter(list);//无输出，判断size()>0生效

        list = new ArrayList<>();
        list.add("测试");
        OptionalUtil.filter(list);//size>0

        list = null;
        //null时
       OptionalUtil.filter(list);//无输出，判断null生效

    }

}
