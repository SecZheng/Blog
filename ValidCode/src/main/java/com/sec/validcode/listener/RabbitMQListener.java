package com.sec.validcode.listener;

import com.sec.validcode.Constants;
import com.sec.validcode.service.QQMailValidCodeService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQListener {
    @Autowired
    QQMailValidCodeService qqMailValidCodeService;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 发送验证码
     */
//    @RabbitListener(bindings = {
//            @QueueBinding(
//                    exchange = @Exchange(Constants.EXCHANGE_CODE),
//                    value = @Queue(Constants.QUEUE_QQ_MAIL),
//                    key = Constants.QUEUE_QQ_MAIL
//            )})
    @RabbitListener(queues = Constants.QUEUE_QQ_MAIL)
    public void qqMail(String email) {
        qqMailValidCodeService.request(email);
    }

}
