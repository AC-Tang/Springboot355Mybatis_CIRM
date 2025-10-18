package com.example.springboot355mybatis_cirm.controller;

import com.example.springboot355mybatis_cirm.entity.Article;
import com.example.springboot355mybatis_cirm.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @GetMapping("/list")
    public ResponseEntity<List<Map<String, Object>>> list(
            @RequestParam(required = false) Integer authorId,
            @RequestParam(required = false) Boolean isDelete,
            @RequestParam(required = false) Integer status) {
        return articleService.getAllArticle(authorId, isDelete, status);
    }

    @PostMapping("/publish")
    public ResponseEntity<Map<String,Object>> publish(
            @RequestPart("article") Article article,                        // 文章 JSON
            @RequestPart(value = "coverImage", required = false) MultipartFile coverImage, // 封面图
            @RequestPart(value = "attachments", required = false) List<MultipartFile> attachments){ // 附件列表
        return articleService.publishArticle(article, coverImage, attachments);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Map<String,Object>> delete(@RequestParam("articleId") Integer articleId){
        return articleService.deleteArticle(articleId);
    }

    @PutMapping("/update")
    public ResponseEntity<Map<String,Object>> update(
            @RequestPart("article") Article article,                        // 文章 JSON
            @RequestPart(value = "coverImage", required = false) MultipartFile coverImage, // 封面图
            @RequestPart(value = "attachments", required = false) List<MultipartFile> attachments){ // 附件列表
        return articleService.updateArticle(article, coverImage, attachments);
    }

    @PostMapping("/submit")
    public ResponseEntity<Map<String,Object>> submit(
            @RequestParam("articleId") Integer articleId,
            @RequestParam(required = false) Integer status){
        return articleService.submitArticle(articleId,status);
    }
}
