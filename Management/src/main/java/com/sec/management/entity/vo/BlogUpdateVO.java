package com.sec.management.entity.vo;

import lombok.Data;
import org.springframework.util.StringUtils;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class BlogUpdateVO {
    @Min(1L)
    @NotNull
    Integer blogId;
    String blogTitle;
    String blogIntro;
    String blogContent;
    Integer views;
    Integer likes;
    Integer blogStatus;
    List<Integer> tagIds;

    public boolean shouldUpdateBlog() {
        return StringUtils.hasText(blogTitle) || StringUtils.hasText(blogIntro)
                || views != null || likes != null || blogStatus != null;
    }

    public boolean shouldUpdateTags() {
        return tagIds != null && tagIds.size() > 0;
    }

    public boolean shouldUpdateContent() {
        return StringUtils.hasText(blogContent);
    }
}
