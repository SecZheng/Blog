package com.sec.singletable.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
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
@TableName("t_blog")
public class Blog implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 博客id
     */
    @TableId(value = "blog_id", type = IdType.AUTO)
    private Integer blogId;

    /**
     * 博客标题
     */
    private String blogTitle;

    private String blogIntro;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 博客状态
     */
    private Integer blogStatus;

    private Integer views;

    private Integer likes;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


    public Integer getBlogId() {
        return blogId;
    }

    public void setBlogId(Integer blogId) {
        this.blogId = blogId;
    }

    public String getBlogTitle() {
        return blogTitle;
    }

    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle;
    }

    public String getBlogIntro() {
        return blogIntro;
    }

    public void setBlogIntro(String blogIntro) {
        this.blogIntro = blogIntro;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBlogStatus() {
        return blogStatus;
    }

    public void setBlogStatus(Integer blogStatus) {
        this.blogStatus = blogStatus;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Blog{" +
        "blogId=" + blogId +
        ", blogTitle=" + blogTitle +
        ", blogIntro=" + blogIntro +
        ", userId=" + userId +
        ", blogStatus=" + blogStatus +
        ", views=" + views +
        ", likes=" + likes +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
