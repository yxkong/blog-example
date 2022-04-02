package com.yxkong.java8;

import com.yxkong.common.test.TestBase;
import org.junit.Test;

/**
 * hashMap测试
 *
 * @Author: yxkong
 * @Date: 2021/8/12 8:51 下午
 * @version: 1.0
 */
public class HashMapTest extends TestBase {
    static final int MAXIMUM_CAPACITY = 1 << 30;



    @Test
    public void tableSizeFor(){
        int i = 1;
        System.out.println(Integer.MAX_VALUE);
        System.out.println((1 << 31)-1 );
        System.out.println(i << 30);
        System.out.println(1 >>> 1);
        System.out.println(tableSizeFor(3));
    }

    /**
     * 修正到最近的2的倍数
     * @param cap
     * @return
     */
    private int tableSizeFor(int cap){
        int n = cap - 1;
        System.out.println(toBinaryString(n));
        n |= n >>> 1;
        System.out.println(toBinaryString(n));
        n |= n >>> 2;
        System.out.println(toBinaryString(n));
        n |= n >>> 4;
        System.out.println(toBinaryString(n));
        n |= n >>> 8;
        System.out.println(toBinaryString(n));
        n |= n >>> 16;
        System.out.println(n + toBinaryString(n));
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;

    }
}