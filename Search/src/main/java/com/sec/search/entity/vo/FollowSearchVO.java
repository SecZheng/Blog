package com.sec.search.entity.vo;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class FollowSearchVO implements Serializable {
    private static final long serialVersionUID = 6676079625808510375L;
    @Min(1L)
    @NotNull
    Integer userId;
    @Min(1L)
    Long pageNum = 1L;
    @Min(1L)
    Long pageSize = 5L;
}
