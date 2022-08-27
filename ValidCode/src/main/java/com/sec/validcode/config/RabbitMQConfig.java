package com.sec.validcode.config;

import com.sec.validcode.Constants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    /**
     * 验证码交换机及队列
     */
    @Bean
    public DirectExchange code(){
        return ExchangeBuilder.directExchange(Constants.EXCHANGE_CODE).build();
    }
    @Bean
    public Queue qqMail(){
        return QueueBuilder.durable(Constants.QUEUE_QQ_MAIL).build();
    }
    @Bean
    public Binding qqMailBinding(){
        return BindingBuilder.bind(qqMail()).to(code()).with(Constants.QUEUE_QQ_MAIL);
    }

}
