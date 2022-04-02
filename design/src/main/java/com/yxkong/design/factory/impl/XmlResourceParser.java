package com.yxkong.design.factory.impl;

import com.yxkong.design.factory.ResourceParser;

/**
 * xml资源解析器
 * @Author: yxkong
 * @Date: 2021/8/10 2:09 下午
 * @version: 1.0
 */
public class XmlResourceParser implements ResourceParser {
    @Override
    public String parser(String filePath) {
        return String.format("%s 文件资源解析器结果", filePath);
    }
}