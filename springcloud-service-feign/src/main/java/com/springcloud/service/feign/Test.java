package com.springcloud.service.feign;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName Test
 * @Description Test
 * @Author gaoyao
 * @Date 2021/2/20 4:05 PM
 * @Version 1.0
 */
public class Test {

    public static void main(String[] args) {
        ConcurrentHashMap<String, Integer>  chm = new ConcurrentHashMap<>();
        chm.put("a", 1);
        chm.put("a", 1);
        System.out.println(chm);
    }

}
