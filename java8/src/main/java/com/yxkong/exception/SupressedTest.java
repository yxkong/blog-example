package com.yxkong.exception;

import net.bytebuddy.implementation.bytecode.Throw;

import java.lang.reflect.Method;

/**
 * <TODO>
 *
 * @Author: yxkong
 * @Date: 2021/10/23 1:37 PM
 * @version: 1.0
 */
public class SupressedTest implements AutoCloseable{
    @Override
    public void close()  {
        throw new RuntimeException("close is error");
    }

    public static void main(String[] args) {
        try (SupressedTest test = new SupressedTest()){
            throw new ClassCastException("try-with-resource");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
