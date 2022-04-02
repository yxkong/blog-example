package com.yxkong.jvm;

/**
 * @Author: yxkong
 * @Date: 2021/9/26 4:07 下午
 * @version: 1.0
 */
public class TryCatchException {

    private static String T1 = "t1";
    private static Integer T2 = 128;
    private static Integer T3 = 256;
    public static void main(String[] args) {
        try {
            Thread.sleep(1000);
            final int i = T3 / T2;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
        }
    }
}