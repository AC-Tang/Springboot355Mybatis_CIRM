package com.example.springboot355mybatis_cirm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot355mybatis_cirm.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

@Service
public interface UserService extends IService<User> {
    ResponseEntity<Map<String, Object>> login(String userName, String password);
    ResponseEntity<Map<String, Object>> register(User user);
    ResponseEntity<Map<String, Object>> updatePassword(Map<String, String> mp);
    ResponseEntity<Map<String, Object>> update(String oldUserName, User user);
    ResponseEntity<Map<String, Object>> getSecurityInfo(String username);
    ResponseEntity<Map<String, Object>> info(String username);
}
