/**
 * @Author: yxkong
 * @Date: 2021/8/10 2:07 下午
 * @version: 1.0
 */
package com.yxkong.design.factory;

/**
 * 资源解析类
 * @author ducongcong
 * @create 2021/8/10
 * @since 1.0.0
 */
public interface ResourceParser {
    /**
     * 将资源解析成字符串
     * @param filePath
     * @return
     */
    public String parser(String filePath);
}
