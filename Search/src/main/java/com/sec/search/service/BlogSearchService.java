package com.sec.search.service;

import com.sec.search.entity.dto.BlogArchiveDTO;
import com.sec.search.entity.dto.BlogDTO;
import com.sec.search.entity.dto.BlogDetailDTO;
import com.sec.search.entity.dto.PageDTO;
import com.sec.search.entity.vo.BlogSearchVO;

import java.util.List;

public interface BlogSearchService {
    BlogDetailDTO detail(Integer blogId);

    PageDTO<List<BlogDTO>> list(BlogSearchVO blogSearchVO);

    List<BlogArchiveDTO> list();
}
