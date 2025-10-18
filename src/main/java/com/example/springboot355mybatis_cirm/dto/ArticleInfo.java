package com.example.springboot355mybatis_cirm.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ArticleInfo {
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
    private Integer pageViews;
    private String tag;
    private String coverImagePath;
    private String coverImageType;

    private String userName;
    private String realName;
    private String categoryName;
}
