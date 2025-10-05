package com.example.springboot355mybatis_cirm.controller;

import com.example.springboot355mybatis_cirm.entity.User;
import com.example.springboot355mybatis_cirm.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.PostExchange;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestParam String userName,@RequestParam String password){
        return userService.login(userName, password);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody User user){
        return userService.register(user);
    }

    @PostMapping("/update")
    public ResponseEntity<Map<String, Object>> update(@RequestParam String oldUserName, @RequestBody User user){
        return userService.update(oldUserName, user);
    }

    @PostMapping("/updatePwd")
    public ResponseEntity<Map<String, Object>> updatePassword(@RequestBody User user){
        return userService.updatePassword(user);
    }
}
