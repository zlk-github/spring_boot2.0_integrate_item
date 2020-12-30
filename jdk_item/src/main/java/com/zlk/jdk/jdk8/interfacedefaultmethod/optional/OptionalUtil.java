package com.zlk.jdk.jdk8.interfacedefaultmethod.optional;

import java.util.Optional;

/**
 * @Description: Optional 判空
 * @Author: ZhouLiKuan
 * @Date: 2020/12/17 16:20
 */
public class OptionalUtil {

    /**
     * 返回一个空的Optional对象
     * @param <T> 对象类型
     * @return 返回一个空的Optional对象
     */
    public static<T> Optional<T> empty(){
        Optional<T> empty = Optional.empty();
        return empty;

    }

    /**
     * 将类转为Optional 对象（常用）
     * @param t 入参对象
     * @param <T> 入参类型
     * @return Optional 对象
     */
    public static<T> Optional<T> ofNullable(T t){
        Optional<T> optional = Optional.ofNullable(t);
        return optional;
    }

    /**
     * 将类转为Optional 对象(入参不能为null，否则会报空指针异常)
     * @param t 入参对象
     * @param <T> 入参类型
     * @return Optional 对象
     */
    public static<T> Optional<T> of(T t){
        Optional<T> optional = Optional.of(t);
        return optional;
    }
}
