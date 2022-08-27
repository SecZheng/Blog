package com.sec.management.entity.vo;

import lombok.Data;
import org.springframework.util.StringUtils;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class UserUpdateVO {
    @Min(value = 1)
    @NotNull
    Integer userId;
    String password;
    String nickname;
    String avatar;

    public boolean shouldUpdateUser(){
        return StringUtils.hasText(password)
                || StringUtils.hasText(nickname)
                || StringUtils.hasText(avatar);
    }
}
