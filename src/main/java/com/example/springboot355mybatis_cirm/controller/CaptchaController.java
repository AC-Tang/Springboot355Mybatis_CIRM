package com.example.springboot355mybatis_cirm.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.util.IdUtil;
import com.example.springboot355mybatis_cirm.dto.CaptchaVerifyDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/captcha")
public class CaptchaController {
    private final StringRedisTemplate redisTemplate;   // 自动配置好

    @GetMapping("/static")
    public ResponseEntity<Map<String, String>> staticCap() {
        Map<String, String> map = new HashMap<>();
        /* ==========  1. 创建验证码  ========== */
        // 宽 200px，高 40px，4 位字符，20 条干扰线
        LineCaptcha captcha = CaptchaUtil.createLineCaptcha(200, 40, 4, 10);

        /* ==========  2. 生成唯一凭证  ========== */
        String uuid = IdUtil.fastSimpleUUID();   // 32 位不带横杠，足够随机

        /* ==========  3. 把正确答案存进 Redis  ========== */
        // key = captcha:static:3f7ece4d7c2f45b2b84e2c7b9d8a0f3e
        // value = 验证码文本（4 位字母/数字）
        // 过期时间 2 分钟（120 秒）
        redisTemplate.opsForValue().set("captcha:static:" + uuid,
                captcha.getCode(),
                Duration.ofMinutes(2));

        map.put("success", "true");
        map.put("uuid", uuid);
        map.put("base64", captcha.getImageBase64());
        return ResponseEntity.ok(map);
    }

    @PostMapping("/verify")
    public ResponseEntity<Map<String, String>> verifyCaptcha(@RequestBody CaptchaVerifyDTO dto) {
        Map<String, String> map = new HashMap<>();
        String key = "captcha:static:" + dto.getUuid();
        String trueCode = redisTemplate.opsForValue().get(key);
        if (trueCode == null) {
            map.put("success", "false");
            map.put("message", "验证码已过期");
            return ResponseEntity.badRequest().body(map);
        }
        if (!trueCode.equalsIgnoreCase(dto.getUserInput())) {
            map.put("success", "false");
            map.put("message", "验证码错误");
            return ResponseEntity.badRequest().body(map);
        }
        // 验完即删，防止复用
        redisTemplate.delete(key);
        map.put("success", "true");
        map.put("message", "验证通过");
        return ResponseEntity.ok(map);
    }
}
