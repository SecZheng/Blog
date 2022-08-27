package com.sec.search.entity.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class FollowDTO implements Serializable {

    private static final long serialVersionUID = 1056646469694677852L;
    private String nickname;

    private String avatar;

}
