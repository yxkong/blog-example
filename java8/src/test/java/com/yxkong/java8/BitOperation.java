package com.yxkong.java8;

import com.yxkong.common.test.TestBase;
import org.junit.Test;

/**
 * @Author: yxkong
 * @Date: 2021/8/13 9:58 上午
 * @version: 1.0
 */
public class BitOperation  extends TestBase {

    @Test
    public void test(){

        printFormat(Integer.MIN_VALUE-1);
        printFormat(-3);
        printFormat(101^32);
        System.out.println(Integer.toBinaryString(-3));
    }
}