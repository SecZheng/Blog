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
@TableName("t_tag")
public class Tag implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 博客标签id
     */
    @TableId(value = "tag_id", type = IdType.AUTO)
    private Integer tagId;

    /**
     * 博客标签名称
     */
    private String tagName;


    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public String toString() {
        return "Tag{" +
        "tagId=" + tagId +
        ", tagName=" + tagName +
        "}";
    }
}
