package com.example.springboot355mybatis_cirm.controller;

import com.example.springboot355mybatis_cirm.entity.User;
import com.example.springboot355mybatis_cirm.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestParam String username,@RequestParam String password){
        return userService.login(username, password);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody User user){
        return userService.register(user);
    }

    @PostMapping("/updatePwd")
    public ResponseEntity<Map<String, Object>> updatePassword(@RequestBody User user){
        return userService.updatePassword(user);
    }
}
