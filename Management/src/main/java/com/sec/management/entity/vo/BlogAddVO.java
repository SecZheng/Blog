package com.sec.management.entity.vo;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class BlogAddVO {
    @NotBlank
    String blogTitle;
    @NotBlank
    String blogContent;
    @Min(1L)
    @NotNull
    Integer userId;
    @Min(0L)
    @Max(1L)
    @NotNull
    Integer blogStatus;
    @NotEmpty
    List<Integer> tagIds;
}
