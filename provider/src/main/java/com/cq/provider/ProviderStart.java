package com.cq.provider;

import com.cq.api.UserServerBo;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author: 陈强
 * @Date: 2018/9/7 16:49
 * @Version 1.0
 */
public class ProviderStart {
    public static void main(String[] args) throws InterruptedException {
        ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("classpath:provider.xml");
        context.start();
        System.out.println("begin");
        System.out.println(context.getBean("userService",UserServerBo.class).sayHello("chenqiang"));
        Thread.currentThread().join();
    }
}
