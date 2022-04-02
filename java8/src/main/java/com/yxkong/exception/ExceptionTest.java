package com.yxkong.exception;

/**
 * <TODO>
 *
 * @Author: yxkong
 * @Date: 2021/10/23 11:18 AM
 * @version: 1.0
 */
public class ExceptionTest implements AutoCloseable {
    public static void main(String[] args) {
        try {
            throw new ClassCastException("无法转化");
        } catch (ClassCastException e) {
            e.printStackTrace();
        } finally {
            System.out.println("finally");
        }
    }

    @Override
    public void close() throws Exception {
        throw new RuntimeException("close is error");
    }
}
