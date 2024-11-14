package com.example.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class GatewayConfig {

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
	    return builder.routes()
	        .route("frontend_route", r -> r
	            .path("/")  // 프론트엔드 경로 구체화
	            .uri("https://localhost:8080")) // 프론트엔드 서버 주소

	        .route("jwt_route", r -> r
	            .path("/jwt/**")
	            .uri("https://localhost:8030")) // 백엔드 서버 주소

	        .route("api_route_1", r -> r
	            .path("/api/**")
	            .and().host("example1.com")  // 예시: 다른 조건 추가
	            .uri("https://localhost:8380"))

	        .route("api_route_2", r -> r
	            .path("/api/**")
	            .and().host("example2.com")  // 예시: 또 다른 조건 추가
	            .uri("https://localhost:8388"))

	        .route("eureka_route", r -> r
	            .path("/api/**")  // Eureka 서버에 특정 경로 지정
	            .uri("https://localhost:8761"))

	        .route("config_route", r -> r
	            .path("/api/**")  // Config 서버에 특정 경로 지정
	            .uri("https://localhost:8888"))

	        .route("another_service_route_1", r -> r
	            .path("/api/**")
	            .uri("https://localhost:8587"))

	        .route("another_service_route_2", r -> r
	            .path("/api/**")
	            .uri("https://localhost:8588"))

	        .build();
	}

}
