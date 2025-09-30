package com.example.springboot355mybatis_cirm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot355mybatis_cirm.entity.User;
import com.example.springboot355mybatis_cirm.mapper.UserMapper;
import com.example.springboot355mybatis_cirm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
        map.put("userName", userName);
        map.put("userPassword", password);
        map.put(("roleName"),user.getRoleName());
        map.put(("success"),true);
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
        }
        return ResponseEntity.ok(map);
    }

    @Override
    public ResponseEntity<Map<String, Object>> updatePassword(User user) {
        Map<String, Object> map = new HashMap<>();
        String username = user.getUserName();
        String newPwd = user.getPassword();
        if (!newPwd.matches("^[A-Za-z0-9]{6,}$")) {
            map.put("success", false);
            map.put("message", "新密码必须是 6 位以上字母或数字");
            return ResponseEntity.badRequest().body(map);
        }

        User dbUser = userMapper.selectOne(new QueryWrapper<User>().eq("user_name", username));
        if (user.getPassword().equals(dbUser.getPassword())) {
            map.put("success", false);
            map.put("message", "新密码不能与旧密码相同");
            return ResponseEntity.badRequest().body(map);
        }
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_name", username);
        updateWrapper.set("password", newPwd);
        int rows = userMapper.update(updateWrapper);
        return ResponseEntity.status(rows == 1 ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("success", rows == 1,
                        "message", rows == 1 ? "密码重置成功" : "更新失败，请重试"));
    }
}
