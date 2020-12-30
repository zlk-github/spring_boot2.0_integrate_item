package com.zlk.jdk.jdk8.interfacedefaultmethod.optional;

import com.zlk.jdk.jdk8.vo.User;
import lombok.Data;
import org.omg.CORBA.Object;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;

/**
 * @Description: Optional
 * @Author: ZhouLiKuan
 * @Date: 2020/12/17 16:20
 */
@Data
public class OptionalUtil {

    private Function<Object, Object> valueFunction;

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
     * ifPresent 存在值则执行，不存在值不做任何事情
     * （可以判断null后不执行，但是不能判断集合里面是否有值，如size()=0也会执行）
     * @param t 入参对象
     * @return Optional 对象
     */
    public static<T> void ifPresent(T t){
        Optional.ofNullable(t).ifPresent(f->{
            //该处可以是方法或者方法块(如果t存在值，执行下面方法或者方法块)
            System.out.println("ifPresent判断存在值，执行结果：" + f);
            //of(t);
        });

        //Optional.ofNullable(t).ifPresent(OptionalUtil::accept);
    }

    /**
     * 如果存在值则返回自己，不存在返回其他（可以是方法调用结果）。（相当于if-else）只对null为其他
     * @param t
     * @param <T>
     * @return
     */
    public static <T> T orElse(T t){
        return Optional.ofNullable(t).orElse( null);
    }

    /**
     * 如果存在值则返回自己，不存在返回其他。（相当于if-else）只对null为其他
     * @param t
     * @param <T>
     * @return
     */
    public static <T> T orElseGet(T t){
        Optional.ofNullable(t).orElseGet( () -> {
            //相较于orElse;orElseGet支持方法返回。
            return get(t);
        });
        return null;
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

    /**
     * 作用：转换取值后返回
     * @param user
     * @param <T>
     * @param <U>
     */
    public static <T, U> void flatMap(User user){
        OptionalUtil optionalUtil = new OptionalUtil();
        //针对非Optional（getName）
        String name = Optional.ofNullable(user).map(u-> u.getName()).get();
        //针对Optional（getCity）
        String city = Optional.ofNullable(user).flatMap(u-> u.getCity()).get();
    }

    /**
     * 判断其他对象是否等于 Optional
     * @param t
     * @param <T>
     */
    public static <T,T2> boolean equals(T t,T2 t2){
       return Optional.ofNullable(t).equals(Optional.ofNullable(t2));
    }

    /**
     * 返回hashCode
     * @param t
     * @param <T>
     * @return
     */
    public static <T> int  hashCode(T t){
        return  Optional.ofNullable(t).hashCode();
    }


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
     * 如果在这个Optional中包含这个值，返回值，否则抛出异常：NoSuchElementException
     * @param t 入参对象
     * @param <T> t
     * @return Optional中的值
     */
    public static<T> T get(T t){
        T value = Optional.ofNullable(t).get();
        return value;
    }


    private static <T> void accept(T f) {
        //该处可以是方法或者方法块(如果t存在值，执行下面方法或者方法块)
        System.out.println("ifPresent判断存在值，执行结果：" + f);
        //of(t);
    }
}
