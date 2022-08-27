package com.sec.singletable.service.impl;

import com.sec.singletable.entity.Tag;
import com.sec.singletable.mapper.TagMapper;
import com.sec.singletable.service.TagService;
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
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

}
