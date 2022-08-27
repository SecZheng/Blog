package com.sec.search.service.impl;

import com.sec.search.service.TagSearchService;
import com.sec.singletable.entity.Tag;
import com.sec.singletable.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagSearchServiceImpl implements TagSearchService {
    @Autowired
    TagService tagService;
    @Override
    @Cacheable("tagList")
    public List<Tag> list() {
        return tagService.list();
    }
}
