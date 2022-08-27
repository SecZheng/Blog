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
@TableName("t_follow")
public class Follow implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 关注id
     */
    @TableId(value = "follow_id", type = IdType.AUTO)
    private Integer followId;

    /**
     * 关注者用户id
     */
    private Integer userId;

    /**
     * 被关注者用户id
     */
    private Integer userFriendId;


    public Integer getFollowId() {
        return followId;
    }

    public void setFollowId(Integer followId) {
        this.followId = followId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserFriendId() {
        return userFriendId;
    }

    public void setUserFriendId(Integer userFriendId) {
        this.userFriendId = userFriendId;
    }

    @Override
    public String toString() {
        return "Follow{" +
        "followId=" + followId +
        ", userId=" + userId +
        ", userFriendId=" + userFriendId +
        "}";
    }
}
