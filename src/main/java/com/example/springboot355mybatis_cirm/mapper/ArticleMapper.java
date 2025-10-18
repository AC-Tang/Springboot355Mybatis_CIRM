package com.example.springboot355mybatis_cirm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot355mybatis_cirm.entity.Article;
import com.example.springboot355mybatis_cirm.dto.ArticleInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
    List<ArticleInfo> getAllArticleInfo(@Param("authorId") Integer authorId,@Param("isDelete") Boolean isDelete,@Param("status") Integer status);
}
