package com.yxkong.other;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.slf4j.spi.MDCAdapter;

import java.util.Date;
import java.util.UUID;

/**
 * @Author: yxkong
 * @Date: 2021/7/28 2:46 下午
 * @version: 1.0
 */
public class Log4jMdcTest {
    private Logger logger = LoggerFactory.getLogger(Log4jMdcTest.class);

    @Test
    public void test(){
        MDC.put("lsh", UUID.randomUUID().toString());
        logger.info("test info level ");
    }
}