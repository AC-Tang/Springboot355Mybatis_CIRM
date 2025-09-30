package com.example.springboot355mybatis_cirm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot355mybatis_cirm.entity.Article;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
}
