package com.zlk.jdk.jdk8.interfacedefaultmethod.optional;

/**
Optional 不是函数是接口，这是个用来防止NullPointerException异常的辅助类型(优化如if (user != null))，方法:
    前面加*代表常用；针对null，不针对size()==0等情况

    *ofNullable(T t): 将类转为Optional 对象（常用）。入参可以为null。非空也是调用 of(T t)。
    *ifPresent(Consumer<? super T> consumer):如果值存在则使用该值调用 consumer , 否则不做任何事情。如果值存在则isPresent()方法会返回true，调用get()方法会返回该对象。
    *orElse(T other):如果存在该值，返回值， 否则返回 other。
    *orElseGet(Supplier<? extends T> other):如果存在该值，返回值， 否则触发 other，并返回 other 调用的结果。
    *orElseThrow(Supplier<? extends X> exceptionSupplier):如果存在该值，返回包含的值，否则抛出由 Supplier 继承的异常
    *filter(Predicate<? super T> predicate)：为辅助的筛选条件，针对如size()==0等

    of(T t):将类转为Optional 对象(入参不能为null，否则会报空指针异常)
    get():如果在这个Optional中包含这个值，返回值，否则抛出异常：NoSuchElementException
    empty()：返回一个空的Optional对象
    flatMap(Function<? super T,Optional<U>> mapper):如果值存在，返回基于Optional包含的映射方法的值，否则返回一个空的Optional
    equals(Object obj):判断其他对象是否等于 Optional。
    hashCode():返回存在值的哈希码，如果值不存在 返回 0。
    *isPresent():如果值存在则方法会返回true，否则返回 false。
    map(Function<? super T,? extends U> mapper):如果有值，则对其执行调用映射函数得到返回值。如果返回值不为 null，则创建包含映射返回值的Optional作为map方法返回值，否则返回空Optional。
    toString():返回一个Optional的非空字符串，用来调试
*/

