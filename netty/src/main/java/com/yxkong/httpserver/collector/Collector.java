package com.yxkong.httpserver.collector;

import com.yxkong.common.entity.dto.ResultBean;

import java.util.Map;

/**
 * 自定义方法收集器
 * @Author: yxkong
 * @Date: 2021/8/17 10:35 下午
 * @version: 1.0
 */
public abstract class Collector {

    /**
     * 自定义方法名称
     */
    protected String methodName;

    public String getMethodName() {
        return methodName;
    }
    /**
     * 抽象实现功能
     * @return
     */
    public abstract ResultBean collect(Map<String,String> params);
    public <T extends Collector> T register() {
        return register(CollectorRegistry.defaultRegistry);
    }
    public <T extends Collector> T register(CollectorRegistry registry) {
        registry.register(this);
        return (T)this;
    }
}