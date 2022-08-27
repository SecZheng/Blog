package com.sec.search.service;

import com.sec.search.entity.dto.FollowDTO;
import com.sec.search.entity.dto.PageDTO;
import com.sec.search.entity.vo.FollowSearchVO;

import java.util.List;

public interface FollowSearchService {

    PageDTO<List<FollowDTO>> list(FollowSearchVO followSearchVO);

}
