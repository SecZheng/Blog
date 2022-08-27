package com.sec.singletable.service.impl;

import com.sec.singletable.entity.Comment;
import com.sec.singletable.mapper.CommentMapper;
import com.sec.singletable.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sec
 * @since 2022-08-12
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
