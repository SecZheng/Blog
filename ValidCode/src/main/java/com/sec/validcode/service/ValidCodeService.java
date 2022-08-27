package com.sec.validcode.service;

public interface ValidCodeService {
    /**
     * 发送验证码
     */
    Boolean request(String email);
}
