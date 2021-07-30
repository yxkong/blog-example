package com.yxkong.other;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.junit.Before;
import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * @Author: yxkong
 * @Date: 2021/7/22 4:04 下午
 * @version: 1.0
 */
public class BloomFilterTest {
    //布隆过滤器的长度
    private int length = 10000;
    private BloomFilter<Integer> bloomFilter;
    //误判率，分别设置  百一 千一  万一
    private double fpp = 0.001;

    @Before
    public void init(){

        bloomFilter = BloomFilter.create(Funnels.integerFunnel(), length, fpp);
    }
    @Test
    public  void test(){
        for (int i = 0; i < length; i++) {
            bloomFilter.put(i);
        }
        int count = 0;
        long start = System.currentTimeMillis();
        /**
         * 设置size个不在bloomFilter中的值进行测试
         */
        int size = 1000;
        for (int i = length ; i < length + size; i++) {
            if (bloomFilter.mightContain(i)) {
                count++;
            }
        }
        System.out.println(String.format("误判的数量：%d", count  ));
        System.out.println(String.format("执行%d 毫秒", (System.currentTimeMillis()-start)));

    }

    //@Test
    public void redis(){
        Config config = new Config();
        config.useMasterSlaveServers().setMasterAddress("redis://192.168.161.219:6380");
        RedissonClient redisson = Redisson.create(config);

        RBloomFilter<String> bloomFilter = redisson.getBloomFilter("bloomFilter");
        /**
         *  初始化布隆过滤器,并添加所有的元素到过滤器
         */
        bloomFilter.tryInit(length,fpp);
        for (int i = 0; i < length; i++) {
            bloomFilter.add(i+"");
        }
        int count = 0;
        long start = System.currentTimeMillis();
        /**
         * 设置size个不在bloomFilter中的值进行测试
         */
        int size = 1000;
        for (int i = length ; i < length + size; i++) {
            if (bloomFilter.contains(i+"")) {
                count++;
            }
        }
        System.out.println(String.format("误判的数量：%d", count  ));
        System.out.println(String.format("执行%d 毫秒", (System.currentTimeMillis()-start)));
    }
}