package com.example.springboot355mybatis_cirm.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI SpringDocAPI(){
        return new OpenAPI()
                .info(new Info().title("平台服务")
                        .description("描述平台信息")
                        .version("v1.0"))
                .externalDocs(new ExternalDocumentation()
                        .description("接口设计文档")
                        .url("http://localhost"));
    }
}
