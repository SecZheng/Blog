package com.sec.search.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sec.blog.constant.RedisConstant;
import com.sec.search.entity.dto.BlogArchiveDTO;
import com.sec.search.entity.dto.BlogDTO;
import com.sec.search.entity.dto.BlogDetailDTO;
import com.sec.search.entity.dto.PageDTO;
import com.sec.search.entity.vo.BlogSearchVO;
import com.sec.search.mapper.ArchiveMapper;
import com.sec.search.service.BlogSearchService;
import com.sec.search.service.CommentSearchService;
import com.sec.singletable.entity.Blog;
import com.sec.singletable.entity.TagBlog;
import com.sec.singletable.service.BlogContentService;
import com.sec.singletable.service.BlogService;
import com.sec.singletable.service.TagBlogService;
import com.sec.singletable.service.TagService;
import com.sec.singletable.service.UserService;
import com.sec.singletable.util.IPUtils;
import org.redisson.api.RBloomFilter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlogSearchServiceImpl implements BlogSearchService, InitializingBean {

    @Autowired
    BlogService blogService;
    @Autowired
    TagBlogService tagBlogService;
    @Autowired
    CommentSearchService commentSearchService;
    @Autowired
    BlogContentService blogContentService;
    @Autowired
    UserService userService;
    @Autowired
    TagService tagService;
    @Autowired
    ArchiveMapper archiveMapper;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    RBloomFilter<Integer> bloomFilter;

    @Override
    @Cacheable(value = "blogDetail")
    public BlogDetailDTO detail(Integer blogId) {
        Blog blog = blogService.getById(blogId);
        if (blog == null) {
            return null;
        }
        String ipKey = RedisConstant.VIEW + blogId + "::" + IPUtils.getIP();
        if (!stringRedisTemplate.hasKey(ipKey)) {
            blog.setViews(blog.getViews() + 1);
            blogService.updateById(blog);
            LocalDateTime now = LocalDateTime.now();
            stringRedisTemplate.opsForValue().set(ipKey, "", Duration.between(now,
                    LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth() + 1, 0, 0)));
        }
        BlogDetailDTO dto = new BlogDetailDTO();
        BeanUtils.copyProperties(blog, dto);
        dto.setBlogContent(blogContentService.getById(blogId).getBlogContent());
        dto.setNickname(userService.getById(blog.getUserId()).getNickname());
        dto.setComments(commentSearchService.list(blogId));
        List<Integer> tagsId = tagBlogService.list(new LambdaQueryWrapper<TagBlog>().eq(TagBlog::getBlogId, blogId))
                .stream()
                .map(TagBlog::getTagId)
                .collect(Collectors.toList());
        if (tagsId.size() == 0) {
            dto.setTags(new ArrayList<>(0));
        } else {
            dto.setTags(tagService.listByIds(tagsId));
        }
        dto.setTotal(dto.getComments().stream()
                .mapToLong(commentDTO -> commentDTO.getOtherComments().size() + 1L)
                .sum());
        return dto;
    }

    @Override
    @Cacheable("blogDTOList")
    public PageDTO<List<BlogDTO>> list(BlogSearchVO blogSearchVO) {
        List<Integer> listBlogIdByTagId = null;
        if (blogSearchVO.getTagId() != null) {
            listBlogIdByTagId = tagBlogService.list(new LambdaQueryWrapper<TagBlog>()
                    .eq(TagBlog::getTagId, blogSearchVO.getTagId()))
                    .stream()
                    .map(TagBlog::getBlogId)
                    .collect(Collectors.toList());
        }
        if (listBlogIdByTagId != null && listBlogIdByTagId.size() == 0) {
            return new PageDTO<>(new ArrayList<>(0), 0L);
        }
        Page<Blog> page = blogService.page(new Page<Blog>(blogSearchVO.getPageNum(), blogSearchVO.getPageSize()),
                new LambdaQueryWrapper<Blog>()
                        .eq(blogSearchVO.getUserId() != null, Blog::getUserId, blogSearchVO.getUserId())
                        .eq(Blog::getBlogStatus, 0)
                        .in(listBlogIdByTagId != null, Blog::getBlogId, listBlogIdByTagId)
                        .like(StringUtils.hasText(blogSearchVO.getSearch()), Blog::getBlogTitle, blogSearchVO.getSearch())
                        .like(StringUtils.hasText(blogSearchVO.getSearch()), Blog::getBlogIntro, blogSearchVO.getSearch()));
        return new PageDTO<>(
                page.getRecords().stream().map(blog -> {
                    BlogDTO blogDTO = new BlogDTO();
                    BeanUtils.copyProperties(blog, blogDTO);
                    blogDTO.setComments(commentSearchService.count(blog.getBlogId()));
                    return blogDTO;
                }).collect(Collectors.toList()), page.getTotal());
    }

    @Override
    @Cacheable("blogArchiveDTOList")
    public List<BlogArchiveDTO> list() {
        List<Integer> years = archiveMapper.years();
        return years.stream().map(year -> {
            BlogArchiveDTO blogArchiveDTO = new BlogArchiveDTO();
            blogArchiveDTO.setYear(year);
            blogArchiveDTO.setList(archiveMapper.list(year)
                    .stream()
                    .map(blogDTO -> {
                        blogDTO.setComments(commentSearchService.count(blogDTO.getBlogId()));
                        return blogDTO;
                    })
                    .collect(Collectors.toList()));
            return blogArchiveDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        blogService.list().stream().map(Blog::getBlogId).forEach(bloomFilter::add);
    }
}
