package com.sec.singletable.service.impl;

import com.sec.singletable.entity.BlogContent;
import com.sec.singletable.mapper.BlogContentMapper;
import com.sec.singletable.service.BlogContentService;
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
public class BlogContentServiceImpl extends ServiceImpl<BlogContentMapper, BlogContent> implements BlogContentService {

}
