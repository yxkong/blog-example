package com.yxkong.delayQueue.service.impl;

import com.yxkong.common.entity.event.MsgContent;
import com.yxkong.delayQueue.service.ProducerStrategy;

import javax.annotation.Resource;

/**
 * kafka
 *
 * @Author: yxkong
 * @Date: 2021/7/27 10:19 上午
 * @version: 1.0
 */
public class KafkaProducerStrategy implements ProducerStrategy {
    //@Resource(name = "bizKafkaTemplate")
    //private KafkaTemplate<Object, Object> kafkaTemplate;
    @Override
    public boolean send(MsgContent content) {
        return false;
    }
}