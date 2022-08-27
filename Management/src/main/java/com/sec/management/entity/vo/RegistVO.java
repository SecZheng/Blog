package com.sec.management.entity.vo;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class RegistVO {
    @Email(message = "不是邮箱格式")
    String email;
    @NotBlank
    String password;
    @NotBlank
    String nickname;
    String avatar;
    @Pattern(regexp = "\\d{6}", message = "有六位数字构成")
    @NotNull
    String code;
}
