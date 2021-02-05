package com.zlk.jdk.jdk8.completablefuture;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

/**
 * @Description: CompletableFuture线程
 * @Author: ZhouLiKuan
 * @Date: 2020/12/17 16:20
 */
public class CompletableFutureUtil {

    public static void main(String[] args) {
      //  CompletableFuture.runAsync()
        int i = Runtime.getRuntime().availableProcessors();
        System.out.println("args = " + i);

    }
}
