package com.example.springboot355mybatis_cirm.entity;

import com.github.javafaker.Bool;
import lombok.Data;
import org.springframework.data.relational.core.sql.In;

import java.util.Date;

@Data
public class Attachment {
    private Integer attachmentId;
    private Integer articleId;   // 外键
    private String fileName;  // 原始文件名
    private String filePath;  // 物理路径
    private Integer fileSize;
    private String fileType;  // 后缀
    private Date uploadTime;
    private Integer uploaderId;
    private Boolean isDelete;
}
