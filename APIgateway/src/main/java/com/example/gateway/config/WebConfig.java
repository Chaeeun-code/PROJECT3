package com.example.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
public class WebConfig {

	   @Bean
	    public CorsWebFilter corsFilter() {
	        CorsConfiguration config = new CorsConfiguration();
	        config.setAllowCredentials(true);
	        config.addAllowedOriginPattern("*"); // 모든 출처 허용
	        config.addAllowedHeader("*"); // 모든 헤더 허용
	        config.addAllowedMethod("*"); // 모든 HTTP 메서드 허용
	        config.addExposedHeader("accessToken");
	        config.addExposedHeader("refreshToken");

	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        source.registerCorsConfiguration("/**", config);

	        return new CorsWebFilter(source);
	    }
}
