package com.example.springboot355mybatis_cirm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(c -> c.configurationSource(corsConfigurationSource()))
                .csrf(c -> c.disable())
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/manager/**").permitAll()
                        .requestMatchers("/user/**").permitAll()
                        .requestMatchers("/article/**").permitAll()
                        .requestMatchers("/category/**").permitAll()
                        .requestMatchers("/attach/**").permitAll()
                        .requestMatchers("/captcha/**").permitAll()
                        .requestMatchers("img/**").permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(withDefaults())
                .formLogin(withDefaults());
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cfg = new CorsConfiguration();
        cfg.addAllowedOriginPattern("*");   // 或写 "http://localhost:5173"
        cfg.addAllowedHeader("*");
        cfg.addAllowedMethod("*");
        cfg.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource src = new UrlBasedCorsConfigurationSource();
        src.registerCorsConfiguration("/**", cfg);
        return src;
    }
}
