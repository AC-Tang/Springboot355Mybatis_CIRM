package com.example.springboot355mybatis_cirm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot355mybatis_cirm.entity.User;
import com.example.springboot355mybatis_cirm.mapper.UserMapper;
import com.example.springboot355mybatis_cirm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public ResponseEntity<Map<String, Object>> login(String userName, String password){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", userName);
        User user = userMapper.selectOne(queryWrapper);
        Map<String, Object> map = new HashMap<>();
        if(user == null){
            map.put("success", false);
            map.put("message", "账号不存在");
            return ResponseEntity.badRequest().body(map);
        }

        if(!user.getPassword().equals(password)){
            map.put("success", false);
            map.put("message", "账号或密码错误");
            return ResponseEntity.badRequest().body(map);
        }
        map.put("userId", user.getUserId());
        map.put("userName", userName);
        map.put("userPassword", password);
        map.put(("roleName"),user.getRoleName());
        map.put(("realName"),user.getRealName());
        map.put(("department"),user.getDepartment());
        map.put(("success"),true);
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_name", userName);
        updateWrapper.set("last_login_time", LocalDateTime.now());
        userMapper.update(updateWrapper);
        return ResponseEntity.ok(map);
    }

    @Override
    public ResponseEntity<Map<String, Object>> register(User user) {
        Map<String, Object> map = new HashMap<>();

        User newUser = new User();
        newUser.setUserName(user.getUserName());
        newUser.setPassword(user.getPassword());
        newUser.setRoleName(user.getRoleName());
        newUser.setRealName(user.getRealName());
        newUser.setDepartment(user.getDepartment());
        newUser.setSecurityQuestion(user.getSecurityQuestion());
        newUser.setSecurityAnswer(user.getSecurityAnswer());

        int rows = userMapper.insert(newUser);
        if(rows == 1){
            map.put("success", true);
        }
        else {
            map.put("success", false);
            map.put("message", "注册失败");
            return ResponseEntity.badRequest().body(map);
        }
        return ResponseEntity.ok(map);
    }

    @Override
    public ResponseEntity<Map<String, Object>> update(String oldUserName, User user) {
        Map<String, Object> map = new HashMap<>();
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_name", oldUserName);
        int rows = userMapper.update(user, updateWrapper);
        if(rows == 1){
            map.put("success", true);
        }
        else {
            map.put("success", false);
            map.put("message", "更新用户信息失败");
            return ResponseEntity.badRequest().body(map);
        }
        return ResponseEntity.ok(map);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getSecurityInfo(String name){
        Map<String, Object> map = new HashMap<>();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", name);
        User user = userMapper.selectOne(queryWrapper);
        if(user == null){
            map.put("success", false);
            map.put("message", "获取密保信息错误");
            return ResponseEntity.badRequest().body(map);
        }
        else {
            map.put("success", true);
            map.put("securityQuestion", user.getSecurityQuestion());
            map.put("securityAnswer", user.getSecurityAnswer());
            map.put("password",user.getPassword());
        }
        return ResponseEntity.ok(map);
    }

    @Override
    public ResponseEntity<Map<String, Object>> updatePassword(Map<String, String> mp) {
        Map<String, Object> map = new HashMap<>();
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_name", mp.get("userName"));
        updateWrapper.set("password", mp.get("password"));
        int rows = userMapper.update(updateWrapper);
        if(rows == 1){
            map.put("success", true);
        }
        else {
            map.put("success", false);
            map.put("message", "更新用户密码失败");
            return ResponseEntity.badRequest().body(map);
        }
        return ResponseEntity.ok(map);
    }

    @Override
    public ResponseEntity<Map<String, Object>> info(String name){
        Map<String, Object> map = new HashMap<>();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", name);
        User user = userMapper.selectOne(queryWrapper);
        if(user == null){
            map.put("success", false);
            map.put("message", "获取用户信息错误");
            return ResponseEntity.badRequest().body(map);
        }
        else {
            map.put("success", true);
            map.put("userName", user.getUserName());
            map.put("userPassword", user.getPassword());
            map.put(("roleName"),user.getRoleName());
            map.put(("realName"),user.getRealName());
            map.put(("department"),user.getDepartment());
            map.put("email",user.getEmail());
        }
        return ResponseEntity.ok(map);
    }
}
