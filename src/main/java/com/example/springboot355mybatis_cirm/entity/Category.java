package com.example.springboot355mybatis_cirm.entity;

import lombok.Data;

@Data
public class Category {
    private Integer categoryId;
    private String name;
    private Integer parentId;
    private Integer level;
    private Integer sortOrder;
    private String description;
}
