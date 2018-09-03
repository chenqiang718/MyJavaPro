package com.cq.springboot.configure;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: 陈强
 * @Date: 2018/8/31 10:48
 * @Version 1.0
 */
@Configuration
public class SendConfirure {
    @Bean
    public Queue queue() {
        return new Queue("queue");
    }

    @Bean(name = "directQueue")
    public Queue directQueue(){
        return new Queue("directQueue");
    }

    @Bean(name = "topicQueue")
    public Queue topicQueue(){
        return new Queue("topicQueue");
    }

    @Bean
    public DirectExchange directexchange(){
        return new DirectExchange("directExhange");
    }

    @Bean
    public TopicExchange topicexchange(){
        return new TopicExchange("topicExchange");
    }

    @Bean
    Binding bindingExchangeMessage(@Qualifier("directQueue") Queue queueMessage, DirectExchange Exchange) {
        return BindingBuilder.bind(queueMessage).to(Exchange).with("direct.message");
    }

    @Bean
    Binding bindingExchangeTopicMessage(@Qualifier("topicQueue") Queue queue,TopicExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("topic.*");
    }

}
