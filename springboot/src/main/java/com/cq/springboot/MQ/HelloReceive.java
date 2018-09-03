package com.cq.springboot.MQ;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Author: 陈强
 * @Date: 2018/8/31 10:59
 * @Version 1.0
 */
@Component
public class HelloReceive {

    @RabbitListener(queues = "queue")
    public void HelloReceive(String msg){
        System.out.println("Receive:"+msg);
    }

    @RabbitListener(queues = "directQueue")
    public void directMsgReceive(String msg){
        System.out.println("DirectReceive:"+msg);
    }

    @RabbitListener(queues = "topicQueue")
    public void topicMsgReceive(String msg){
        System.out.println("TopicReceive:"+msg);
    }

}
