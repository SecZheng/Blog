package com.sec.management.entity.vo;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CommentAddVO {
    @NotBlank
    String commentContent;
    @Min(1L)
    @NotNull
    Integer userId;
    @NotNull
    @Min(0L)
    Integer blogId;
    @Min(0L)
    Integer replyId = 0;
}
