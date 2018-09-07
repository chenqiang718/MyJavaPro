package com.cq.consumer;

import com.cq.api.UserServerBo;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author: 陈强
 * @Date: 2018/9/7 16:55
 * @Version 1.0
 */
public class ConsumerStart {
    public static void main(String[] args) throws InterruptedException {
        ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("classpath:consumer.xml");
        context.start();
        UserServerBo userServerBo=context.getBean("userService",UserServerBo.class);
        System.out.println(userServerBo.sayHello("hahaha"));
    }
}
