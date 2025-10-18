package com.example.springboot355mybatis_cirm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot355mybatis_cirm.entity.Article;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public interface ArticleService extends IService<Article> {
    ResponseEntity<List<Map<String, Object>>> getAllArticle(@RequestParam(required = false) Integer authorId,@RequestParam(required = false) Boolean isDelete,Integer status);
    ResponseEntity<Map<String, Object>> publishArticle(Article article, MultipartFile coverImage,List<MultipartFile> files);
    ResponseEntity<Map<String, Object>> deleteArticle(Integer articleId);
    ResponseEntity<Map<String, Object>> updateArticle(Article article, MultipartFile coverImage,List<MultipartFile> files);
    ResponseEntity<Map<String, Object>> submitArticle(Integer articleId,Integer status);
}
