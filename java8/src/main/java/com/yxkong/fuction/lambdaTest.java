package com.yxkong.fuction;

import io.swagger.models.auth.In;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * <TODO>
 *
 * @Author: yxkong
 * @Date: 2021/10/19 4:33 PM
 * @version: 1.0
 */
public class lambdaTest {

    /**
     * 消费型，内部消化，无返回
     * @param age
     * @param consumer
     */
    public static void consumer1(Integer age, Consumer<Integer> consumer){
        consumer.accept(age);
    }

    /**
     * 供给型，给外部提供有返回
     * @param num
     * @param supplier
     * @return
     */
    public static List<Integer> supply(Integer num, Supplier<Integer> supplier){
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            final Integer integer = supplier.get();
            System.err.println(integer);
            list.add(integer);
        }
        return list;
    }
    public static List<String> filter(List<String> fruit, Predicate<String> predicate){
        List<String> list = new ArrayList<>();
        fruit.forEach(s->{
            if (predicate.test(s)){
                list.add(s);
            }
        });
        return list;
    }

    public static void main(String[] args) {
        consumer1(18, age -> System.out.println("你的年龄为"+age+"岁"));
        supply(10,()-> (int)(Math.random()*100)).forEach(System.out::println);
        filter(Arrays.asList("香蕉","苹果","葡萄","哈密瓜"), (f)->f.length() == 2).forEach(System.out::println);
    }
}
