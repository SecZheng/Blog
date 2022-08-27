package com.sec.validcode.service.impl;

import com.sec.blog.constant.RedisConstant;
import com.sec.validcode.service.QQMailValidCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class QQMailValidCodeServiceImpl implements QQMailValidCodeService {
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    String username;

    @Override
    public Boolean request(String email) {
        int code = generateCode();
        System.out.println("code = " + code);
        return send(email, code);
    }

    private Boolean send(String email, int code) {
        try {
            stringRedisTemplate.opsForValue().set(RedisConstant.VALID_CODE + email, String.valueOf(code), 60, TimeUnit.SECONDS);
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            //邮件发送人
            simpleMailMessage.setFrom(username);
            //邮件接收人
            simpleMailMessage.setTo(email);
            //邮件主题
            simpleMailMessage.setSubject("验证码");
            //邮件内容
            simpleMailMessage.setText("验证码:" + code + "，有效时间60秒。");
            javaMailSender.send(simpleMailMessage);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private int generateCode() {
        int code = 0;
        for (int i = 0; i < 6; i++) {
            code = code * 10 + ((int) (Math.random() * 10));
        }
        return code;
    }
}
