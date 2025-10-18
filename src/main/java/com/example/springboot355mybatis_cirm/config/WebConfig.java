package com.example.springboot355mybatis_cirm.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry reg) {
        // 访问  http://localhost:8080/img/3f7ece4d-xxxx.jpg
        // 实际走 D:/JAVA-.../uploads/image/
        reg.addResourceHandler("/img/**")
                .addResourceLocations("file:D:/JAVA-面向程序设计/Springboot355Mybatis_CIRM/uploads/image/");
        reg.addResourceHandler("/attach/**")
                .addResourceLocations("file:D:/JAVA-面向程序设计/Springboot355Mybatis_CIRM/uploads/attachment/");
    }
}
