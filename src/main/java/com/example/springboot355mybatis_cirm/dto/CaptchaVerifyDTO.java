package com.example.springboot355mybatis_cirm.dto;

import lombok.Data;

@Data
public class CaptchaVerifyDTO {
    private String uuid;
    private String userInput;
}
