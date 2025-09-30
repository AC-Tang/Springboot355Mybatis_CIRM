package com.example.springboot355mybatis_cirm.entity;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private Integer userId;
    private String userName;
    private String password;
    private String realName;
    private String email;
    private String department;
    private Integer status;
    private Date createTime;
    private Date lastLoginTime;

    private String securityQuestion;
    private String securityAnswer;

    private String roleName;
}
