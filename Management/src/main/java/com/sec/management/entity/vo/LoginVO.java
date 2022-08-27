package com.sec.management.entity.vo;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class LoginVO {
    @Email(message = "不是邮箱格式")
    @NotBlank
    String email;
    @NotBlank
    String password;
}
