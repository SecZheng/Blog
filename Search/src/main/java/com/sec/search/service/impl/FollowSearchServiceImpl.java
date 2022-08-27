package com.sec.search.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sec.search.entity.dto.FollowDTO;
import com.sec.search.entity.dto.PageDTO;
import com.sec.search.entity.vo.FollowSearchVO;
import com.sec.search.service.FollowSearchService;
import com.sec.singletable.entity.Follow;
import com.sec.singletable.service.FollowService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FollowSearchServiceImpl implements FollowSearchService {
    @Autowired
    FollowService followService;

    @Override
    @Cacheable("followDTOList")
    public PageDTO<List<FollowDTO>> list(FollowSearchVO followSearchVO) {
        Page<Follow> page = followService.page(new Page<>(),
                new LambdaQueryWrapper<Follow>().eq(Follow::getUserId, followSearchVO.getUserId()));
        return new PageDTO<>(page.getRecords().stream().map(follow -> {
                    FollowDTO followDTO = new FollowDTO();
                    BeanUtils.copyProperties(follow, followDTO);
                    return followDTO;
                }).collect(Collectors.toList()),
                page.getTotal());
    }
}
