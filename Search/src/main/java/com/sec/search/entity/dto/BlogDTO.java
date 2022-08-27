package com.sec.search.entity.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BlogDTO implements Serializable {

    private static final long serialVersionUID = -1601132150170601596L;
    private Integer blogId;

    private String blogTitle;

    private String blogIntro;

    private Integer userId;

    private Integer blogStatus;

    private Integer views;

    private Integer comments;

    private Integer likes;

    private Date updateTime;

}
