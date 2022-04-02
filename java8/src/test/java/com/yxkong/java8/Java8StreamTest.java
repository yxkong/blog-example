package com.yxkong.java8;

import com.yxkong.dto.LoginInfo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author: yxkong
 * @Date: 2021/7/26 10:36 上午
 * @version: 1.0
 */
public class Java8StreamTest {
    List<LoginInfo> list = null;
    Map<String,LoginInfo> map = null;
    private int getAge(){
        int age =new Random().nextInt(100);
        return age>18 ? age : 18;
    }
    private long getDate(){
        long det = new Random().nextLong();
        return System.currentTimeMillis() - det;
    }
    private String getLoginSource(){
        String[] sources = {"ios","android","h5"};
        return sources[new Random().nextInt(sources.length)];
    }
    private String getNickName(int i){
        String[] nicks = {"yxk","yxkong","tao","java"};
        return nicks[new Random().nextInt(nicks.length)] +i;
    }
    private String getMobile(int i){
        long[] nicks = {15600000000L,13600000000L,15100000000L,13300000000L};
        return nicks[new Random().nextInt(nicks.length)] + Long.valueOf(i) +"";
    }
    @Before
    public void init(){
        list = new ArrayList<>();
        map = new HashMap<>();
        LoginInfo info = null;
        for (int i = 0; i < 100; i++) {
            info = new LoginInfo(Long.valueOf(i),getMobile(i),getNickName(i),getDate(),getLoginSource(),getAge(),getDate());
            list.add(info);
            map.put(info.getMobile(),info);
        }

    }
    @Test
    public void generate(){
        Stream.iterate(0, (x) -> x + 2).limit(5).forEach(System.out::println);
        Stream.generate(()-> Math.random()).limit(5L).forEach(System.out::println);
    }
    @Test
    public void map(){
        list.stream().map(s -> s.getNickName()).limit(3).sorted().forEach(System.out::println);
        list.stream().flatMap(s->Stream.of(s.getNickName())).limit(3).sorted().forEachOrdered(System.out::println);
    }

    @Test
    public void distinctAndSorted(){
        int[] nums = {5,1,3,3,1};
        Arrays.stream(nums).distinct().sorted().forEach(System.out::println);
        list.stream().distinct().sorted((a,b)-> a.getMobile().compareTo(b.getMobile())).limit(3).forEach(System.out::println);
        list.stream().distinct().sorted((a,b)-> b.getMobile().compareTo(a.getMobile())).limit(3).forEach(System.out::println);

        list.stream().distinct().limit(3).sorted((a,b)-> a.getMobile().compareTo(b.getMobile())).forEach(System.out::println);
        list.stream().distinct().limit(3).sorted((a,b)-> b.getMobile().compareTo(a.getMobile())).forEach(System.out::println);
    }
    @Test
    public void skipAndPeek(){
        list.stream().skip(98).peek(s-> s.setAge(s.getAge()+100)).forEach(System.out::println);
    }
    @Test
    public void collect(){
        //转成list
        final List<LoginInfo> list1 = list.stream().filter(l -> l.getAge() > 18 && l.getAge() < 30).collect(Collectors.toList());
        list1.forEach(l-> System.out.println("list:"+l.getUserId()));
        //转成set
        final Set<LoginInfo> set = list.stream().filter(l -> l.getAge() > 18 && l.getAge() < 30).collect(Collectors.toSet());
        set.forEach(l-> System.out.println("set:"+l.getUserId()));
        //转成map key是userId,value 是 LoginInfo
        final Map<Long, LoginInfo> map = list.stream().filter(l -> l.getAge() > 18 && l.getAge() < 30).collect(Collectors.toMap(LoginInfo::getUserId, l -> l));
        map.forEach((k,v)-> System.out.println("map:"+k));
        //转成map  key是userId,value 是age
        final Map<Long, Integer> map1 = list.stream().filter(l -> l.getAge() > 18 && l.getAge() < 30).collect(Collectors.toMap(LoginInfo::getUserId, LoginInfo::getAge));
        // 按loginSource 进行分组
        final Map<String, List<LoginInfo>> map2 = list.stream().collect(Collectors.groupingBy(LoginInfo::getLoginSource));
        map2.forEach((k,v)-> System.out.println(k+" size:"+ v.size()));
        // 年龄大于60进行分组
        final ConcurrentMap<Boolean, List<Integer>> concurrentMap = list.stream().flatMap(l -> Stream.of(l.getAge())).collect(Collectors.groupingByConcurrent(l -> l.intValue() > 60));
        concurrentMap.forEach((k,v)-> System.out.println(k+" size:"+ v.size()));
        // 将nickName 拼接起来 [yxkong0,yxk1,java2...]
        final String collect = list.stream().map(LoginInfo::getNickName).collect(Collectors.joining(",","[","]"));
        System.out.println(collect);
    }
    @Test
    public void foreach(){
        //foreach无序
        list.stream().parallel().limit(5).forEach(System.out::println);
        //forEachOrdered 按.sorted的顺序
        list.stream().sorted((a,b)->a.getUserId().compareTo(b.getUserId())).parallel().limit(5).forEachOrdered(System.out::println);
    }
    @Test
    public void toArray(){
        final Object[] objects = list.stream().limit(5).toArray();
        Arrays.stream(objects).forEach(System.out::println);
        //明确指定类型LoginInfo
        final LoginInfo[] infos = list.stream().limit(5).toArray(LoginInfo[]::new);
        Arrays.stream(infos).forEach(System.out::println);
    }
    @Test
    public void maxAndMinAndCount(){
        final Optional<LoginInfo> max = list.stream().filter(l -> l.getAge() < 18).max((m, n) -> m.getUserId().compareTo(n.getUserId()));
        Assert.assertFalse("没有符合条件的数据",max.isPresent());
        final Optional<LoginInfo> max1 = list.stream().max((m, n) -> m.getUserId().compareTo(n.getUserId()));
        System.out.println(max1.get());
        final Optional<LoginInfo> min = list.stream().min((m, n) -> m.getUserId().compareTo(n.getUserId()));
        System.out.println(min.get());
        final long count = list.stream().count();
        Assert.assertEquals("总数等于100",100,count);
    }
    @Test
    public  void shortCircuiting(){
        final boolean noneMatch = list.stream().noneMatch(l -> l.getAge() > 100);
        Assert.assertTrue("没有符合年龄大于100的数据",noneMatch);
        final boolean anyMatch1 = list.stream().anyMatch(l -> l.getAge() > 90);
        Assert.assertTrue("有符合条件的数据",anyMatch1);
        final boolean allMatch = list.stream().allMatch(l -> l.getAge() >= 18);
        Assert.assertTrue("所有登录用户都满足大于等于18",allMatch);
        final Optional<LoginInfo> first = list.stream().findFirst();
        System.out.println(first.get());
        final Optional<LoginInfo> any = list.stream().findAny();
        final Optional<LoginInfo> any1 = list.stream().findAny();
        final Optional<LoginInfo> any2 = list.stream().findAny();
        Assert.assertTrue("都相等",any.equals(any1) && any.equals(any2));
        final Optional<LoginInfo> pany = list.stream().parallel().findAny();
        final Optional<LoginInfo> pany1 = list.stream().parallel().findAny();
        final Optional<LoginInfo> pany2 = list.stream().parallel().findAny();
        Assert.assertFalse("在parallel情况下不相等",pany.equals(pany1) && pany.equals(pany2));

    }
    @Test
    public void reduce(){
        int[] nums = {5,1,3,3,1};
        final OptionalInt reduceSum = Arrays.stream(nums).reduce(Integer::sum);
        final int sum = Arrays.stream(nums).sum();
        Assert.assertEquals(reduceSum.getAsInt(),sum);
        final OptionalInt reduceMax = Arrays.stream(nums).reduce(Integer::max);
        final OptionalInt max = Arrays.stream(nums).max();
        Assert.assertEquals(reduceMax,max);
    }
    @Test
    public void array(){
        Integer[] nums = {1,3,4,5,6,7};
        final long count = Stream.of(nums).filter(s -> s % 2 == 0).count();
        System.out.println(count);
        int[] nums1 = {1,3,4,5,6,7,8,9};
        final double aDouble = Arrays.stream(nums1).sum();
        System.out.println(aDouble);

    }

    @Test
    public void filter(){
        /**
         * 我们过滤得到年龄>18 且小于30的登录渠道为h5的用户
         */
        list.stream().filter(s->s.getAge()>18 && s.getAge()<30)
                .filter(s->"h5".equals(s.getLoginSource())).forEach(System.out::println);
        boolean exist = list.stream().filter(s->s.getAge()>18 && s.getAge()<30)
                .filter(s->"h5".equals(s.getLoginSource())).anyMatch(s->s.getNickName().contains("yxk"));
        System.out.println(exist);
    }
}