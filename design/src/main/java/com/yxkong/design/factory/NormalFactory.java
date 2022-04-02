package com.yxkong.design.factory;

import com.yxkong.design.factory.impl.JsonResourceParser;
import com.yxkong.design.factory.impl.PropertyResourceParser;
import com.yxkong.design.factory.impl.XmlResourceParser;
import com.yxkong.design.factory.impl.YamlResourceParser;

import java.util.HashMap;
import java.util.Map;

/**
 * 普通工厂
 * @Author: yxkong
 * @Date: 2021/8/10 2:05 下午
 * @version: 1.0
 */
public class NormalFactory {
    /**
     * 在spring里，我们可以利用spring的DI机制，将同一个类型的实现注入到一个map中
     * 然后key我们做的有规律些，
     * 在获取的时候 根据业务类型，直接推算出key，直接从map中获取对象。
     */
    private static Map<String,ResourceParser> map = new HashMap<>();
    static {
        map.put("xml", new XmlResourceParser());
        map.put("json", new JsonResourceParser());
        map.put("yaml",new YamlResourceParser());
        map.put("property",new PropertyResourceParser());
    }

    public ResourceParser createParser(String filePath){
        String extention = getExtention(filePath);
        return map.get(extention);
    }
    private String getExtention(String filePath){
        return filePath.split("\\.")[1];
    }

    public static void main(String[] args) {
        String filePath = "application.yaml";
        String parser = new NormalFactory().createParser(filePath).parser(filePath);
        System.out.println(parser);
        filePath = "application.property";
        parser = new NormalFactory().createParser(filePath).parser(filePath);
        System.out.println(parser);
    }
}