package com.yxkong.delayQueue.service.impl;

import com.yxkong.common.entity.event.MsgContent;
import com.yxkong.delayQueue.service.ProducerStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

/**
 * @Author: yxkong
 * @Date: 2021/7/27 10:20 上午
 * @version: 1.0
 */
public class ApplicationProducerStrategy implements ProducerStrategy {
    @Autowired
    private ApplicationEventPublisher publisher;
    @Override
    public boolean send(MsgContent content) {
        return true;
    }
}