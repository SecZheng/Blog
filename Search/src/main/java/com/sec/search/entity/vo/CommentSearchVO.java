package com.sec.search.entity.vo;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class CommentSearchVO implements Serializable {
    private static final long serialVersionUID = 1748891378279426420L;
    @Min(1L)
    @NotNull
    Integer blogId;
    @Min(0L)
    Integer replyId;
    @Min(1L)
    Long pageNum = 1L;
    @Min(1L)
    Long pageSize = 5L;
}
