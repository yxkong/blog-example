package com.yxkong.common.test;

/**
 * @Author: yxkong
 * @Date: 2021/8/13 9:24 上午
 * @version: 1.0
 */
public class TestBase {
    protected void printFormat(int n){
        out(format(n));
    }
    protected void printFormat(long n){
        out(format(n));
    }
    protected void out(Object obj){
        System.out.println(obj);
    }
    protected void err(Object obj){
        System.err.println(obj);
    }
    protected String format(long n){
        return format(toBinaryString(n),64);
    }
    private String format(String str,int len){
        if (len > str.length()){
            str = String.format("%0"+(len - str.length())+"d",0)+str;
        }
        int i = 1;
        StringBuilder sb = new StringBuilder();
        for (char c:str.toCharArray()) {
            sb.append(c);
            if (i == 4){
                sb.append(" ");
                i = 0;
            }
            i++;
        }
        return sb.toString();
    }
    protected String format(int n){

        return format(toBinaryString(n),32);
    }
    /**
     * 输出int类型的二进制
     * @param n
     * @return
     */
    protected String toBinaryString(int n){
        return  Integer.toBinaryString(n);
    }

    /**
     * 输出long类型的二进制
     * @param n
     * @return
     */
    protected String toBinaryString(long n){
        return  Long.toBinaryString(n);
    }
}