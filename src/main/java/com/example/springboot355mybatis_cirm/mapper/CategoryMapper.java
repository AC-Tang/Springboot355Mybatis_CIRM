package com.example.springboot355mybatis_cirm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot355mybatis_cirm.entity.Category;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
