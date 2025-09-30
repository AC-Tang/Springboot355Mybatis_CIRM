package com.example.springboot355mybatis_cirm.entity;

import lombok.Data;

import java.util.Date;

@Data
public class AuditLog {
    private Integer logId;
    private Integer articleId;
    private Integer auditorId;
    private Date Time;
    private Integer Result;
    private String Comment;
}
