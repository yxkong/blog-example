package com.yxkong.exception;

/**
 * <TODO>
 *
 * @Author: yxkong
 * @Date: 2021/10/24 10:50 AM
 * @version: 1.0
 */
public class Foo {
    public void read(int a,int b){
        int c = a+b;
        int x = c + a;
        int y = c + b;
    }
    public void write(int a){
        a = 1;
        a = 2;
    }
    public void fortest(int x,int y){
        for (int i = 0; i < 10; i++) {
            int  m = x*y+i;
        }
    }


}

class Bar{

}
