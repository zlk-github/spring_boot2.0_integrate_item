package com.zlk.jdk.interfacedefaultmethod.stream;

import com.zlk.jdk.jdk8.interfacedefaultmethod.optional.OptionalUtil;
import com.zlk.jdk.jdk8.interfacedefaultmethod.stream.StreamUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @Description: Optional 判空
 * @Author: ZhouLiKuan
 * @Date: 2020/12/17 16:20
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class StreamUtilTest {

    @Test
    public void testEmpty(){
        List<String> list = new ArrayList<>();
        list.add("1111");
        list.add("2222");
        list.add("2222");
        long count = StreamUtil.filter(list,"2222");
        System.out.println(count);
    }

    @Test
    public void maxOrMin(){
        List<String> list = new ArrayList<>();
        list.add("1111");
        list.add("2222");
        list.add("2222");
        list.add("5555");
        list.add("5555");
        StreamUtil.maxOrMin(list);
        //max:5555
        //min:1111
    }

}
