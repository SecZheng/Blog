package com.sec.search.entity.vo;

import lombok.Data;

import javax.validation.constraints.Min;
import java.io.Serializable;

@Data
public class BlogSearchVO implements Serializable {
    private static final long serialVersionUID = -3965879962478965239L;
    @Min(1L)
    Integer userId;
    @Min(1L)
    Integer tagId;
    String search;
    @Min(1L)
    Long pageNum=1L;
    @Min(1L)
    Long pageSize=5L;
}
