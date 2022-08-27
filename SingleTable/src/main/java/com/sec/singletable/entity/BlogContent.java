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
@TableName("t_blog_content")
public class BlogContent implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "blog_id", type = IdType.AUTO)
    private Integer blogId;

    private String blogContent;


    public Integer getBlogId() {
        return blogId;
    }

    public void setBlogId(Integer blogId) {
        this.blogId = blogId;
    }

    public String getBlogContent() {
        return blogContent;
    }

    public void setBlogContent(String blogContent) {
        this.blogContent = blogContent;
    }

    @Override
    public String toString() {
        return "BlogContent{" +
        "blogId=" + blogId +
        ", blogContent=" + blogContent +
        "}";
    }
}
