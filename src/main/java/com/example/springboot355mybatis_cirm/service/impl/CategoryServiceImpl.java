package com.example.springboot355mybatis_cirm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot355mybatis_cirm.entity.Article;
import com.example.springboot355mybatis_cirm.entity.Category;
import com.example.springboot355mybatis_cirm.mapper.CategoryMapper;
import com.example.springboot355mybatis_cirm.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ResponseEntity<List<Map<String, Object>>> getAllCategory(){
        List<Map<String, Object>> mapList = new ArrayList<>();
        List<Category> categoryList = categoryMapper.selectList(null);
        if(categoryList.isEmpty()){
            return new ResponseEntity<>(mapList, HttpStatus.NO_CONTENT);
        }
        for(Category category : categoryList){
            Map<String, Object> map = new HashMap<>();
            map.put("name", category.getName());
            map.put("categoryId", category.getCategoryId());
            map.put("parentId", category.getParentId());
            map.put("level", category.getLevel());
            map.put("sortOrder", category.getSortOrder());
            map.put("description", category.getDescription());
            mapList.add(map);
        }
        return ResponseEntity.ok(mapList);
    }
}
