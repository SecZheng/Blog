package com.sec.management.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.sec.blog.constant.RedisConstant;
import com.sec.blog.entity.R;
import com.sec.blog.util.IPdbUtils;
import com.sec.management.entity.vo.CommentAddVO;
import com.sec.management.entity.vo.LoginVO;
import com.sec.management.entity.vo.RegistVO;
import com.sec.management.entity.vo.UserUpdateVO;
import com.sec.singletable.entity.Comment;
import com.sec.singletable.entity.Follow;
import com.sec.singletable.entity.User;
import com.sec.singletable.service.CommentService;
import com.sec.singletable.service.FollowService;
import com.sec.singletable.service.UserService;
import com.sec.singletable.util.IPUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class UserController {
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    UserService userService;
    @Autowired
    FollowService followService;
    @Autowired
    CommentService commentService;

    @PostMapping("/login")
    public R<User> login(@Validated @RequestBody LoginVO loginVO) {
        if (userService.update(new LambdaUpdateWrapper<User>().set(User::getLastLogin, LocalDateTime.now())
                .eq(User::getEmail, loginVO.getEmail())
                .eq(User::getPassword, loginVO.getPassword()))) {
            return R.ok(userService.getOne(new LambdaQueryWrapper<User>().eq(User::getEmail, loginVO.getEmail())));
        }
        return R.ok(null);
    }

    @PostMapping("/regist")
    public R<User> regist(@Validated @RequestBody RegistVO registVO) {
        if (exists(registVO.getEmail()).getData()) {
            return R.ok();
        }
        if (registVO.getCode().equals(stringRedisTemplate.opsForValue()
                .get(RedisConstant.VALID_CODE + registVO.getEmail()))) {
            User user = new User();
            BeanUtils.copyProperties(registVO, user);
            stringRedisTemplate.delete(RedisConstant.VALID_CODE + registVO.getEmail());
            if (userService.save(user)) {
                return R.ok(user);
            }
        }
        return R.ok(null, "注册失败");
    }

    @PostMapping("/user")
    public R<Boolean> updateUser(@Validated @RequestBody UserUpdateVO userUpdateVO) {
        if (userUpdateVO.shouldUpdateUser()) {
            User user = new User();
            BeanUtils.copyProperties(userUpdateVO, user);
            return R.ok(userService.updateById(user));
        }
        return R.ok();
    }

    @GetMapping("/exists")
    public R<Boolean> exists(@RequestParam String email) {
        return R.ok(userService.getOne(new LambdaQueryWrapper<User>().eq(User::getEmail, email)) != null);
    }


    @PostMapping("/follow")
    public R<Follow> addFollow(@RequestParam Integer userId, @RequestParam Integer userFriendId) {
        Follow follow = new Follow();
        follow.setUserId(userId);
        follow.setUserFriendId(userFriendId);
        if (followService.save(follow)) {
            return R.ok(follow);
        }
        return R.ok(null, "关注失败");
    }

//    @DeleteMapping("/follow")
    public R<Boolean> deleteBlog(@RequestParam Integer followId) {
        return R.ok(followService.removeById(followId));
    }

    @PostMapping("/comment")
    @Caching(
            evict = {
                    @CacheEvict(value = "blogCommentDTO",key = "#commentAddVO.blogId"),
                    @CacheEvict(value = "blogDetail",key = "#commentAddVO.blogId")
            }
    )
    public R<Comment> addComment(@RequestBody @Validated CommentAddVO commentAddVO) {
        Comment comment = new Comment();
        comment.setProvince(IPdbUtils.find(IPUtils.getIP()));
        BeanUtils.copyProperties(commentAddVO, comment);
        if (commentService.save(comment)) {
            return R.ok(comment);
        }
        return R.ok(null, "评论失败");
    }

//    @DeleteMapping("/comment")
    @Transactional
    public R<Boolean> deleteComment(@RequestParam Integer commentId) {
        commentService.remove(new LambdaQueryWrapper<Comment>().eq(Comment::getReplyId, commentId));
        return R.ok(commentService.removeById(commentId));
    }

    @PostMapping("/other")
    public R<Comment> other(@RequestBody Comment comment) {
        comment.setReplyId(-1);
        if (commentService.save(comment)) {
            return R.ok(comment);
        }
        return R.ok(null, "评论失败");
    }
}
