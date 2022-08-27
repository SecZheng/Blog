package com.sec.validcode.controller;

import com.sec.blog.constant.RedisConstant;
import com.sec.blog.entity.R;
import com.sec.validcode.Constants;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Pattern;

@RestController
@RequestMapping("/validCode")
public class ValidCodeController {
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    Pattern pattern = Pattern.compile("^([a-z0-9_.-]+)@([\\da-z.-]+)\\.([a-z.]{2,6})$");

    @RequestMapping("/QQMail")
    public R<String> code(@RequestParam String email) {
        if (!pattern.matcher(email).find()) {
            return R.ok("邮箱错误");
        }
        if (stringRedisTemplate.hasKey(RedisConstant.VALID_CODE + email)) {
            return R.ok("60s后重新发送");
        }
        rabbitTemplate.convertAndSend(Constants.EXCHANGE_CODE, Constants.QUEUE_QQ_MAIL, email);
        //qq邮箱发送大概需要20s
        return R.ok("预计需要20秒，请耐心等待");
    }

}
