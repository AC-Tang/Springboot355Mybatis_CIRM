package com.example.springboot355mybatis_cirm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot355mybatis_cirm.entity.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public interface CategoryService extends IService<Category> {
    ResponseEntity<List<Map<String, Object>>> getAllCategory();
}
