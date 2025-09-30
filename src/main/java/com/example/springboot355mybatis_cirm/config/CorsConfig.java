package com.example.springboot355mybatis_cirm.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //允许跨域访问路径
        registry.addMapping("/**")
                //允许跨域访问的源
                //.allowedOrigins("*") //旧版本用法
                .allowedOriginPatterns("*")
                //允许请求的方法
                .allowedMethods("POST", "GET", "PUT", "PATCH", "OPTIONS", "DELETE")
                //预间隔时间
                .maxAge(168000)
                //允许头部设置
                .allowedHeaders("*")
                //是否发送cookie
                .allowCredentials(true);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/public/**")
                .addResourceLocations("classpath:/static/");
    }
}
