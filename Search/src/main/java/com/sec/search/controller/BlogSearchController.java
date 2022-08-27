package com.sec.search.controller;

import com.sec.blog.entity.R;
import com.sec.search.entity.dto.BlogArchiveDTO;
import com.sec.search.entity.dto.BlogDTO;
import com.sec.search.entity.dto.BlogDetailDTO;
import com.sec.search.entity.dto.PageDTO;
import com.sec.search.entity.vo.BlogSearchVO;
import com.sec.search.service.BlogSearchService;
import org.redisson.api.RBloomFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BlogSearchController {
    @Autowired
    BlogSearchService blogSearchService;
    @Autowired
    RBloomFilter<Integer> bloomFilter;

    @RequestMapping("/blog/{blogId}")
    public R<BlogDetailDTO> detail(@PathVariable Integer blogId) {
        if (!bloomFilter.contains(blogId)) {
            return R.ok();
        }
        return R.ok(blogSearchService.detail(blogId));
    }

    @PostMapping("/list/blog")
    public R<PageDTO<List<BlogDTO>>> list(@RequestBody @Validated BlogSearchVO blogSearchVO) {
        return R.ok(blogSearchService.list(blogSearchVO));
    }

    @PostMapping("list/archive")
    public R<List<BlogArchiveDTO>> list() {
        return R.ok(blogSearchService.list());
    }

}
