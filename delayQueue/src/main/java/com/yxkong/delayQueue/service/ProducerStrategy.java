package com.yxkong.delayQueue.service;

import com.yxkong.common.entity.event.MsgContent;

/**
 * 生产者策略
 * @Author: yxkong
 * @Date: 2021/7/27 10:05 上午
 * @version: 1.0
 */
public interface ProducerStrategy {

    boolean send(MsgContent content);
}