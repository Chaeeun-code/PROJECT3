package com.app.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
    @LoadBalanced  // Eureka를 통한 서비스 이름 호출 지원
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}


