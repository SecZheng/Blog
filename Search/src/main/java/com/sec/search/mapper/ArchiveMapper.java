package com.sec.search.mapper;

import com.sec.search.entity.dto.BlogDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ArchiveMapper {

    @Select("select distinct year(update_time) as year from t_blog order by year desc")
    List<Integer> years();

    @Select("select * from t_blog where year(update_time) = #{year}")
    List<BlogDTO> list(Integer year);
}
