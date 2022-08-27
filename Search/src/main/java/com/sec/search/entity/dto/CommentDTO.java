package com.sec.search.entity.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class CommentDTO implements Serializable {

    private static final long serialVersionUID = -149623918884456607L;
    private Integer commentId;

    private String commentContent;

    private Integer userId;

    private Integer blogId;

    private Integer replyId;

    String replyName;

    List<CommentDTO> otherComments;

    private Date createTime;

    private String nickname;

    private String avatar;

    String province;

}
