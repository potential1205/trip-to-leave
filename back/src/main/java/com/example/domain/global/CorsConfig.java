package com.example.domain.global;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000") // 클라이언트 도메인
                .allowCredentials(true) // 쿠키 허용
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
    }
}