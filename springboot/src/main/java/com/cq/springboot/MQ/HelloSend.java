package com.cq.springboot.MQ;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: 陈强
 * @Date: 2018/8/31 10:52
 * @Version 1.0
 */
@Component
public class HelloSend {
    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendHello(){
        amqpTemplate.convertAndSend("queue","Hello");
    }

    public void sendDirect(){
        amqpTemplate.convertSendAndReceive("directExhange", "direct.message","dicectHello");
    }

    public void sendTopic(){
        amqpTemplate.convertSendAndReceive("topicExchange", "topic.message","哈哈哈Topic");
    }
}
