package com.sec.search.controller;

import com.sec.blog.entity.R;
import com.sec.search.entity.dto.CommentDTO;
import com.sec.search.entity.dto.FollowDTO;
import com.sec.search.entity.dto.PageDTO;
import com.sec.search.entity.vo.CommentSearchVO;
import com.sec.search.entity.vo.FollowSearchVO;
import com.sec.search.service.CommentSearchService;
import com.sec.search.service.FollowSearchService;
import com.sec.search.service.TagSearchService;
import com.sec.singletable.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OtherSearchController {
    @Autowired
    CommentSearchService commentSearchService;
    @Autowired
    FollowSearchService followSearchService;
    @Autowired
    TagSearchService tagSearchService;

    @PostMapping("/list/comment")
    public R<PageDTO<List<CommentDTO>>> list(@RequestBody @Validated CommentSearchVO commentSearchVO) {
        return R.ok(commentSearchService.list(commentSearchVO));
    }

//    @PostMapping("/list/follow")
    public R<PageDTO<List<FollowDTO>>> list(@RequestBody @Validated FollowSearchVO followSearchVO) {
        return R.ok(followSearchService.list(followSearchVO));
    }

    @PostMapping("/list/tag")
    public R<List<Tag>> list() {
        return R.ok(tagSearchService.list());
    }
}
