package com.yxkong.httpserver.collector;

import com.yxkong.common.entity.dto.ResultBean;

import java.util.*;

/**
 * 收集器注册
 * @Author: yxkong
 * @Date: 2021/8/17 10:42 下午
 * @version: 1.0
 */
public class CollectorRegistry {
    public static final CollectorRegistry defaultRegistry = new CollectorRegistry();

    /**
     * 声明一个jvm全局锁
     */
    private static final Object namesCollectorsLock = new Object();
    /**
     * 存放menthodName 和collector的映射
     */
    private final Map<String, Collector> namesToCollectors = new HashMap<String, Collector>(16);
    public CollectorRegistry() {
    }
    /**
     * 注册一个Collector
     */
    public void register(Collector m) {
        String name =  m.getMethodName();
        synchronized (namesCollectorsLock) {
            if (namesToCollectors.containsKey(name)) {
                throw new IllegalArgumentException("Collector already registered that provides name: " + name);
            }
            namesToCollectors.put(name, m);
        }
    }
    /**
     * 卸载所有的 Collectors.
     */
    public void clear() {
        synchronized (namesCollectorsLock) {
            namesToCollectors.clear();
        }
    }
    /**
     * 卸载一个Collector.
     */
    public void unregister(Collector m) {
        synchronized (namesCollectorsLock) {
            namesToCollectors.remove(m.getMethodName());
        }
    }
    private Set<Collector> collectors() {
        synchronized (namesCollectorsLock) {
            return new HashSet<Collector>(namesToCollectors.values());
        }
    }
    /**
     * 获取Collector 并执行对应的collect()
     * @param name
     * @param params
     * @return
     */
    public ResultBean filteredCollector(String name, Map<String,String> params ) {
        Collector collector = namesToCollectors.get(name);
        if (Objects.isNull(collector)){
            return null;
        }
        return  collector.collect(params);
    }
}