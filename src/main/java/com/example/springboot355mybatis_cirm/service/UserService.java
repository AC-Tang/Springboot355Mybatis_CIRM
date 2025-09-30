package com.example.springboot355mybatis_cirm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot355mybatis_cirm.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public interface UserService extends IService<User> {
    ResponseEntity<Map<String, Object>> login(String username, String password);
    ResponseEntity<Map<String, Object>> register(User user);
    ResponseEntity<Map<String, Object>> updatePassword(User user);
}
