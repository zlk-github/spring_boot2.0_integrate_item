package com.zlk.jdk.interfacedefaultmethod.optional;
/**
 * Optional 不是函数是接口，这是个用来防止NullPointerException异常的辅助类型，方法:
 *     前面加*代表常用
 *
 *     empty()：返回一个空的Optional对象
 *     *ofNullable(T t): 将类转为Optional 对象（常用）。入参可以为null。非空也是调用 of(T t)。
 *     of(T t):将类转为Optional 对象(入参不能为null，否则会报空指针异常)
 *
 */
