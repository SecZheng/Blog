package com.sec.singletable.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author sec
 * @since 2022-08-12
 */
@TableName("t_tag_blog")
public class TagBlog implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 博客标签id
     */
    private Integer tagId;

    /**
     * 博客id
     */
    private Integer blogId;


    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public Integer getBlogId() {
        return blogId;
    }

    public void setBlogId(Integer blogId) {
        this.blogId = blogId;
    }

    @Override
    public String toString() {
        return "TagBlog{" +
        "tagId=" + tagId +
        ", blogId=" + blogId +
        "}";
    }
}
