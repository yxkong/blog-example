package com.yxkong.design.singleton;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 饿汉式单例
 * 1，在类加载的时候，instance就已经初始化好了，确保了实例的唯一性
 * 2，如果实例加载过慢，会增长类加载的过程
 *
 * @Author: yxkong
 * @Date: 2021/8/11 8:22 下午
 * @version: 1.0
 */
public class IdWorker {
    private AtomicLong id = new AtomicLong(0);
    private static final IdWorker instance = new IdWorker();
    private IdWorker(){

    }
    public static IdWorker getInstance(){
        return instance;
    }
    public Long getId(){
        return id.getAndIncrement();
    }
}

/**
 * 懒汉式加载
 * 懒加载（延迟加载）
 * 在使用的时候去实例化，需要加锁
 * 不管有没有，进来先加锁，虽然synchronized已经做了锁优化，但是还是会加锁和释放锁
 */
class IdWorker1{
    private AtomicLong id = new AtomicLong(0);
    private static IdWorker1 instance;
    private IdWorker1(){

    }

    /**
     * 锁住类IdWorker1
     * @return
     */
    public static synchronized IdWorker1 getInstance(){
        if (Objects.isNull(instance)){
            instance = new IdWorker1();
        }
        return instance;
    }
    public Long getId(){
        return id.getAndIncrement();
    }
}

/**
 * double check机制
 */
class IdWorker2{
    private AtomicLong id = new AtomicLong(0);
    private static IdWorker2 instance;
    private IdWorker2(){}
    public static IdWorker2 getInstance(){
        if (Objects.isNull(instance)){
            /**
             * 锁住类
             */
            synchronized (IdWorker2.class){
                // 多线程并发时，多个线程加锁，一个释放后，另一个再来操作，再次检测一下
                if (Objects.isNull(instance)){
                    instance = new IdWorker2();
                }
            }
        }
        return instance;
    }
    public Long getId(){
        return id.getAndIncrement();
    }
}

class IdWorker3{
    private AtomicLong id = new AtomicLong(0);
    private IdWorker3(){}
    //在类IdWorker3Builder 类加载的时候会执行静态初始化
    private static class IdWorker3Builder{
        private static final IdWorker3 instance = new IdWorker3();
    }
    public static IdWorker3 getInstance(){
        return IdWorker3Builder.instance;
    }
    public Long getId(){
        return id.getAndIncrement();
    }
}
