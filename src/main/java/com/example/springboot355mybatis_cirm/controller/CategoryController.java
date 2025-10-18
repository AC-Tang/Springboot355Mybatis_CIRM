package com.example.springboot355mybatis_cirm.controller;

import com.example.springboot355mybatis_cirm.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public ResponseEntity<List<Map<String, Object>>> list() {
        return categoryService.getAllCategory();
    }
}
