package com.yxkong.httpserver.collector.impl;

import com.yxkong.common.entity.dto.ResultBean;
import com.yxkong.httpserver.collector.Collector;

import java.util.Map;

/**
 * @Author: yxkong
 * @Date: 2021/8/17 10:50 下午
 * @version: 1.0
 */
public class StackCollector extends Collector {
    public StackCollector() {
        this.methodName = "stack";
    }

    @Override
    public ResultBean collect(Map<String, String> params) {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        return new ResultBean.Builder().success(elements).build();
    }
}