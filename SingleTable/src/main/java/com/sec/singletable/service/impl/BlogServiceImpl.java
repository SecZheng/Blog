package com.sec.singletable.service.impl;

import com.sec.singletable.entity.Blog;
import com.sec.singletable.mapper.BlogMapper;
import com.sec.singletable.service.BlogService;
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
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

}
