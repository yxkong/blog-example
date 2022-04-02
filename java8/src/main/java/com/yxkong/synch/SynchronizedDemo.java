package com.yxkong.synch;

import java.util.concurrent.locks.ReentrantLock;

/**
 * <TODO>
 *
 * @Author: yxkong
 * @Date: 2021/10/8 10:48 上午
 * @version: 1.0
 */
public class SynchronizedDemo {
    static ReentrantLock lock = new ReentrantLock();
    public static void main(String[] args) {
        synchronized (lock){
            System.out.println("locked 1");
            synchronized (lock){
                System.out.println("locked 2");
            }
        }

    }
//    public void demo1(Integer age){
//        synchronized (this){
//            System.out.println("demo1:"+age);
//        }
//    }
//    public synchronized void demo2(Integer age){
//        System.out.println("demo2:"+age);
//    }
//
//    public static synchronized void demo3(Integer age){
//        System.out.println("demo3:"+age);
//    }

//    public static void main(String[] args) {
//        SynchronizedDemo demo = new SynchronizedDemo();
//        demo.demo2(18);
//        SynchronizedDemo.demo3(20);
//    }
}
