package com.yxkong.design.factory;

import com.yxkong.design.factory.impl.JsonResourceParser;
import com.yxkong.design.factory.impl.PropertyResourceParser;
import com.yxkong.design.factory.impl.XmlResourceParser;
import com.yxkong.design.factory.impl.YamlResourceParser;

/**
 * 简单工厂
 * @Author: yxkong
 * @Date: 2021/8/10 2:05 下午
 * @version: 1.0
 */
public class SimpleFactory {

    public static ResourceParser createParser(String filePath){
        String extention = getExtention(filePath);
        if ("xml".equalsIgnoreCase(extention)){
            return new XmlResourceParser();
        } else if ("json".equalsIgnoreCase(extention)){
            return new JsonResourceParser();
        } else if ("yaml".equalsIgnoreCase(extention)){
            return new YamlResourceParser();
        } else if ("property".equalsIgnoreCase(extention)){
            return new PropertyResourceParser();
        }
        throw new RuntimeException("未找到对应的解析器");
    }
    private static String getExtention(String filePath){
        return filePath.split("\\.")[1];
    }

    public static void main(String[] args) {
        String filePath = "application.yaml";
        String parser = SimpleFactory.createParser(filePath).parser(filePath);
        System.out.println(parser);
        filePath = "application.property";
        parser = SimpleFactory.createParser(filePath).parser(filePath);
        System.out.println(parser);
    }
}