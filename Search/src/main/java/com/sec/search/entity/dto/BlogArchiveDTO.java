package com.sec.search.entity.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class BlogArchiveDTO implements Serializable {
    private static final long serialVersionUID = 5081976034767547425L;
    Integer year;
    List<BlogDTO> list;
}
