package com.example.springboot355mybatis_cirm.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Article {
    private int articleId;
    private String title;
    private String content;
    private String summary;
    private Integer categoryId;
    private Integer authorId;
    private Integer status;
    private Integer isTop;
    private Date publishTime;
    private Date createTime;
    private Date updateTime;
}
