package com.yxkong.thread;

import java.util.concurrent.*;

/**
 * <TODO>
 *
 * @Author: yxkong
 * @Date: 2021/10/11 2:50 PM
 * @version: 1.0
 */
public class ThreadPoolTest {
    public static void main(String[] args) {

        int len = 100;
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2,4,1000, TimeUnit.MILLISECONDS,new ArrayBlockingQueue<>(2*len));
        for (int i = 0; i < len-1; i++) {
            int finalI = i;
            Future future = executor.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println("执行了"+finalI);
                    try {
                        Thread.sleep(1);
                    }catch (Exception e){

                    }finally {

                    }
                    int j = 100/0;
                    throw new RuntimeException("xxx");
                }
            });
            executor.submit(new ResultThread(future,finalI));
        }

    }
}
class  ResultThread implements Runnable{
    private  Future future ;
    private int num;

    public ResultThread(Future future,int num) {
        this.future = future;
        this.num = num;
    }
    @Override
    public void run() {
        try {
            System.out.println(String.format("num:{} isDone: {}, object:{}",num,future.isDone(),future.get() ));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }
}
