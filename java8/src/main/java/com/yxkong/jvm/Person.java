package com.yxkong.jvm;

import com.yxkong.synch.SynchronizedDemo;

/**
 * @Author: yxkong
 * @Date: 2021/9/26 4:07 下午
 * @version: 1.0
 */
public class Person {

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public synchronized void setAge(int age) {
        this.age = age;
    }

    public void demo1(Integer age){
        synchronized (this){
            System.out.println("demo1:"+age);
        }
    }
    public synchronized void demo2(Integer age){
        System.out.println("demo2:"+age);
    }

    public static synchronized void demo3(Integer age){
        System.out.println("demo3:"+age);
    }

    public static void main(String[] args) {
        SynchronizedDemo demo = new SynchronizedDemo();
        demo.demo2(18);
        SynchronizedDemo.demo3(20);
    }
}