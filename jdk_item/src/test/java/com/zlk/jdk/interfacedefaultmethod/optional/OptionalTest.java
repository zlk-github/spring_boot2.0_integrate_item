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
        List<String> list1 = new ArrayList<>();
        Optional<Object> o = Optional.of(list1);
        o = OptionalUtil.of(list1);
        System.out.println(o);
    }


}
