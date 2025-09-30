package com.example.springboot355mybatis_cirm.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Attachment {
    private Integer attachmentId;
    private String fileName;
    private String filePath;
    private Integer fileSize;
    private Date uploadTime;
    private Integer uploaderId;
}
