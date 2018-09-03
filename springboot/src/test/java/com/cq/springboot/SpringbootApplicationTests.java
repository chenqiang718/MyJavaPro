package com.cq.springboot;

import com.cq.springboot.MQ.HelloReceive;
import com.cq.springboot.MQ.HelloSend;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootApplication.class)
public class SpringbootApplicationTests {

    @Autowired
    private HelloSend helloSend;

    @Test
    public void send() {
        helloSend.sendHello();
    }

    @Test
    public void direceSend(){
        helloSend.sendDirect();
    }

    @Test
    public void topicSend(){
        helloSend.sendTopic();
    }
}
