package com.sec.management.advice;

import com.sec.blog.constant.RedisConstant;
import com.sec.blog.entity.R;
import com.sec.singletable.util.IPUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Aspect
@Component
public class FluentlyOperateAdvice {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Around("execution(* com.sec.management.controller.*.add*(..))" +
            "||execution(* com.sec.management.controller.*.update*(..))" +
            "||execution(* com.sec.management.controller.*.delete*(..))")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        String ipKey = RedisConstant.OP + IPUtils.getIP();
        LocalDateTime now = LocalDateTime.now();
        String value = stringRedisTemplate.opsForValue().get(ipKey);
        if (value == null) {
            stringRedisTemplate.opsForValue().set(ipKey, "0", Duration.between(now,
                    LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth() + 1, 0, 0)));
        } else {
            Integer integer = Integer.valueOf(value);
            if (integer > 100) {
                return R.error("更新操作频繁");
            }
            stringRedisTemplate.opsForValue().set(ipKey, String.valueOf(integer+1), Duration.between(now,
                    LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth() + 1, 0, 0)));
        }
        return pjp.proceed();
    }
}
