package com.sec.search.service;

import com.sec.search.entity.dto.CommentDTO;
import com.sec.search.entity.dto.PageDTO;
import com.sec.search.entity.vo.CommentSearchVO;

import java.util.List;

public interface CommentSearchService {

    PageDTO<List<CommentDTO>> list(CommentSearchVO commentSearchVO);

    Integer count(Integer blogId);

    List<CommentDTO> list(Integer blogId);
}
