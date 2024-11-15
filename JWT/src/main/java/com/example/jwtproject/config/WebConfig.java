package com.example.jwtproject.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://localhost:8443") // 허용할 프론트엔드 서버 주소
                .allowedHeaders("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS" , "PATCH")
                .exposedHeaders("accessToken", "refreshToken")
                .allowCredentials(true);
    }
}
