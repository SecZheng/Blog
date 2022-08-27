package com.sec.management.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.sec.blog.constant.RedisConstant;
import com.sec.blog.entity.R;
import com.sec.management.entity.vo.BlogAddVO;
import com.sec.management.entity.vo.BlogUpdateVO;
import com.sec.singletable.entity.Blog;
import com.sec.singletable.entity.BlogContent;
import com.sec.singletable.entity.TagBlog;
import com.sec.singletable.service.BlogContentService;
import com.sec.singletable.service.BlogService;
import com.sec.singletable.service.TagBlogService;
import com.sec.singletable.util.IPUtils;
import org.redisson.api.RBloomFilter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    BlogService blogService;
    @Autowired
    TagBlogService tagBlogService;
    @Autowired
    BlogContentService blogContentService;
    @Autowired
    RBloomFilter<Integer> bloomFilter;

//    @PostMapping
    @Transactional
    public R<Blog> addBlog(@RequestBody @Validated BlogAddVO blogAddVO) {
        Blog blog = new Blog();
        BlogContent blogContent = new BlogContent();
        BeanUtils.copyProperties(blogAddVO, blog);
        BeanUtils.copyProperties(blogAddVO, blogContent);
        if (blogService.save(blog) && blogContentService.save(blogContent)) {
            bloomFilter.add(blog.getBlogId());
            blogAddVO.getTagIds().forEach(tagId -> {
                TagBlog tagBlog = new TagBlog();
                tagBlog.setBlogId(blog.getBlogId());
                tagBlog.setTagId(tagId);
                tagBlogService.save(tagBlog);
            });
            return R.ok(blog);
        }
        return R.ok(null, "添加失败");
    }

//    @DeleteMapping
    @Transactional
    @CacheEvict("blogDetail")
    public R<Boolean> deleteBlog(@RequestParam Integer blogId) {
        tagBlogService.remove(new LambdaQueryWrapper<TagBlog>().eq(TagBlog::getBlogId, blogId));
        return R.ok(blogService.removeById(blogId));
    }

//    @PutMapping
    @Transactional
    @CacheEvict("blogDetail")
    public R<Boolean> updateBlog(@RequestBody @Validated BlogUpdateVO blogUpdateVO) {
        boolean updated = false;
        if (blogUpdateVO.shouldUpdateContent()) {
            BlogContent blogContent = new BlogContent();
            BeanUtils.copyProperties(blogUpdateVO, blogContent);
            blogContentService.updateById(blogContent);
            updated = true;
        }
        if (blogUpdateVO.shouldUpdateBlog()) {
            Blog blog = new Blog();
            BeanUtils.copyProperties(blogUpdateVO, blog);
            blogService.updateById(blog);
            updated = true;
        }
        if (blogUpdateVO.shouldUpdateTags()) {
            tagBlogService.remove(new LambdaQueryWrapper<TagBlog>().eq(TagBlog::getBlogId, blogUpdateVO.getBlogId()));
            blogUpdateVO.getTagIds().forEach(tagId -> {
                TagBlog tagBlog = new TagBlog();
                tagBlog.setBlogId(blogUpdateVO.getBlogId());
                tagBlog.setTagId(tagId);
                tagBlogService.saveOrUpdate(tagBlog);
            });
            updated = true;
        }
        return R.ok(updated);
    }

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @CacheEvict("blogDetail")
    @PostMapping("/likes")
    public R<Boolean> likes(@RequestBody Blog blog) {
        String ipKey = RedisConstant.LIKES + blog.getBlogId() + "::" + IPUtils.getIP();
        if (!stringRedisTemplate.hasKey(ipKey)) {
            blogService.update(new LambdaUpdateWrapper<Blog>().setSql("likes = likes + 1")
                    .eq(Blog::getBlogId, blog.getBlogId()));
            LocalDateTime now = LocalDateTime.now();
            stringRedisTemplate.opsForValue().set(ipKey, "", Duration.between(now,
                    LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth() + 1, 0, 0)));
        }
        return R.ok();
    }
}
