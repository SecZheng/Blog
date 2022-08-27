package com.sec.search.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sec.search.entity.dto.CommentDTO;
import com.sec.search.entity.dto.PageDTO;
import com.sec.search.entity.vo.CommentSearchVO;
import com.sec.search.service.CommentSearchService;
import com.sec.singletable.entity.Comment;
import com.sec.singletable.entity.User;
import com.sec.singletable.service.CommentService;
import com.sec.singletable.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CommentSearchServiceImpl implements CommentSearchService {
    @Autowired
    CommentService commentService;
    @Autowired
    UserService userService;

    @Override
    @Cacheable("commentDTOList")
    public PageDTO<List<CommentDTO>> list(CommentSearchVO commentSearchVO) {
        Page<Comment> page = commentService.page(
                new Page<>(commentSearchVO.getPageNum(), commentSearchVO.getPageSize()),
                new LambdaQueryWrapper<Comment>()
                        .eq(Comment::getBlogId, commentSearchVO.getBlogId())
                        .eq(commentSearchVO.getReplyId() != null, Comment::getReplyId, commentSearchVO.getReplyId()));
        List<Integer> ids = page.getRecords().stream().map(Comment::getUserId).collect(Collectors.toList());
        List<User> users = userService.listByIds(ids);
        List<CommentDTO> list = new ArrayList<>(users.size());
        for (int i = 0; i < users.size(); i++) {
            CommentDTO dto = new CommentDTO();
            BeanUtils.copyProperties(page.getRecords().get(i), dto);
            dto.setNickname(users.get(i).getNickname());
            dto.setAvatar(users.get(i).getAvatar());
            list.add(dto);
        }
        return new PageDTO<>(list, page.getTotal());
    }

    @Override
    @Cacheable("blogCommentCount")
    public Integer count(Integer blogId) {
        return (int)commentService.count(new LambdaQueryWrapper<Comment>().eq(Comment::getBlogId, blogId));
    }

    @Override
    @Cacheable("blogCommentDTO")
    public List<CommentDTO> list(Integer blogId) {
        List<Comment> list = commentService.list(new LambdaQueryWrapper<Comment>().eq(Comment::getBlogId, blogId));
        if (list.size() == 0) {
            return new ArrayList<>(0);
        }
        List<Integer> ids = list.stream().map(Comment::getUserId).collect(Collectors.toList());
        List<User> users = userService.listByIds(ids);
        List<CommentDTO> commentDTOList = new ArrayList<>(users.size());
        for (int i = 0; i < list.size(); i++) {
            CommentDTO dto = new CommentDTO();
            Comment comment = list.get(i);
            BeanUtils.copyProperties(comment, dto);
            User user = users.stream().filter(u -> u.getUserId().equals(comment.getUserId())).findFirst().get();
            if (comment.getReplyId() > 0) {
                Comment replyComment = list.stream().filter(c -> c.getCommentId().equals(comment.getReplyId())).findFirst().get();
                Integer replyUserId = replyComment.getUserId();
                User replyUser = users.stream().filter(u -> replyUserId.equals(u.getUserId())).findFirst().get();
                dto.setReplyName(replyUser.getNickname());
            }
            dto.setNickname(user.getNickname());
            dto.setAvatar(user.getAvatar());
            commentDTOList.add(dto);
        }
        List<CommentDTO> main = commentDTOList.stream().filter(commentDTO -> commentDTO.getReplyId() == 0).collect(Collectors.toList());
        return main.stream().map(commentDTO -> {
            List<CommentDTO> root = listByRoot(commentDTO.getCommentId(), commentDTOList);
            commentDTO.setOtherComments(root);
            return commentDTO;
        }).collect(Collectors.toList());
    }

    private List<CommentDTO> listByRoot(Integer commentId, List<CommentDTO> list) {
        List<CommentDTO> children = list.stream().filter(commentDTO -> commentDTO.getReplyId() == commentId).collect(Collectors.toList());
        if (children.size() > 0) {
            return Stream.concat(children.stream(),
                    children.stream()
                            .flatMap(commentDTO -> listByRoot(commentDTO.getCommentId(), list).stream()))
                    .collect(Collectors.toList());

        } else {
            return children;
        }
    }
}
