package com.sec.search.entity.dto;

import com.sec.singletable.entity.Tag;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class BlogDetailDTO implements Serializable {

    private static final long serialVersionUID = 287879285875203217L;
    private Integer blogId;

    private String blogTitle;

    private String blogContent;

    private Integer userId;

    private String nickname;

    private Integer blogStatus;

    private Integer views;

    private List<CommentDTO> comments;

    private Integer likes;

    private Date updateTime;

    List<Tag> tags;

    Long total;


}
