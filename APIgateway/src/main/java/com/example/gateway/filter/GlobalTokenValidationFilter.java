package com.example.gateway.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import reactor.core.publisher.Mono;
import org.springframework.web.reactive.function.client.WebClient;
import java.net.URI;
import java.util.List;

//@Component
public class GlobalTokenValidationFilter implements GlobalFilter, Ordered {

    @Value("${auth.server.url}")
    private String authServerUrl;

    
    
    private final WebClient webClient;
    
    public GlobalTokenValidationFilter(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    private static final List<String> STATIC_RESOURCE_PATHS = List.of(
        "/js/", "/css/", "/grim/", "/static/", "/frontend/", "/webjars/", "/favicon.ico"
    );

    private static final List<String> EXCLUDED_PATHS = List.of("/", "/index", "/login", "/signup", "/company");

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        System.out.println("Request path: " + path);

        // 정적 리소스 또는 예외 경로는 필터 생략
        if (isStaticResourcePath(path) || EXCLUDED_PATHS.contains(path)) {
            System.out.println("Excluded or static path detected. Skipping filter.");
            return chain.filter(exchange);
        }

        // 모든 refreshToken 쿠키를 가져옴
        String refreshToken = extractRefreshToken(exchange.getRequest());

        // 리프레시 토큰이 없는 경우 로그인 페이지로 리디렉션
        if (refreshToken == null) {
            System.out.println("No refresh token found. Redirecting to login.");
            return redirectToLogin(exchange);
        }

        return reissueAccessToken(refreshToken)
                .flatMap(newAccessToken -> {
                    System.out.println("New access token issued: " + newAccessToken);
                    return validateAccessToken(newAccessToken)
                        .flatMap(isValid -> {
                            if (isValid) {
                                System.out.println("Access token validated successfully.");
                                return chain.filter(exchange);
                            } else {
                                System.out.println("Access token validation failed. Redirecting to login.");
                                return redirectToLogin(exchange);
                            }
                        });
                })
                .switchIfEmpty(Mono.defer(() -> {
                    System.out.println("Failed to reissue access token. Redirecting to login.");
                    return redirectToLogin(exchange);
                }));
        }

        // 정적 리소스 경로 확인 메서드
        private boolean isStaticResourcePath(String path) {
            return STATIC_RESOURCE_PATHS.stream().anyMatch(path::startsWith);
        }

        // 쿠키에서 리프레시 토큰 추출
        private String extractRefreshToken(ServerHttpRequest request) {
            HttpCookie refreshTokenCookie = request.getCookies().getFirst("refreshToken");
            String token = (refreshTokenCookie != null) ? refreshTokenCookie.getValue() : null;
            System.out.println("Extracted refresh token: " + token);
            return token;
        }



    // 새 액세스 토큰 발급을 시도하는 메서드
    private Mono<String> reissueAccessToken(String refreshToken) {
        return webClient.post()
                .uri(authServerUrl + "/jwt/refresh")
                .cookies(cookies -> cookies.add("refreshToken", refreshToken))  // 쿠키로 리프레시 토큰 전달
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(token -> System.out.println("Access token reissued successfully. Token: " + token))
                .onErrorResume(error -> {
                    System.out.println("Error reissuing access token: " + error.getMessage());
                    return Mono.empty();
                });
    }

    // 발급된 액세스 토큰의 유효성을 검증하는 메서드
    private Mono<Boolean> validateAccessToken(String accessToken) {
        System.out.println("Extracted refresh token: " + accessToken);

        // JSON에서 "token" 필드 값만 추출
        String tokenValue;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(accessToken);
            tokenValue = jsonNode.get("token").asText();  // "token" 필드 값 추출
        } catch (Exception e) {
            System.out.println("Error parsing access token JSON: " + e.getMessage());
            return Mono.just(false);  // JSON 파싱 실패 시 유효성 검증 실패 처리
        }

        return webClient.get()
                .uri(authServerUrl + "/jwt/validate")
                .header("Authorization", "Bearer " + tokenValue)  // 추출한 token 값으로 Authorization 헤더 설정
                .retrieve()
                .toBodilessEntity()
                .map(response -> {
                    boolean isValid = response.getStatusCode() == HttpStatus.OK;
                    System.out.println("Access token validation result: " + isValid);
                    return isValid;
                })
                .onErrorResume(error -> {
                    System.out.println("Error validating access token: " + error.getMessage());
                    return Mono.just(false);
                });
    }

    // 특정 URI로 리디렉션하는 메서드
    private Mono<Void> redirectToUri(ServerWebExchange exchange, String uri) {
        System.out.println("Redirecting to URI: " + uri);
        exchange.getResponse().setStatusCode(HttpStatus.FOUND);
        exchange.getResponse().getHeaders().setLocation(URI.create(uri));
        return exchange.getResponse().setComplete();
    }

    // 로그인 페이지로 리디렉션
    private Mono<Void> redirectToLogin(ServerWebExchange exchange) {
        return redirectToUri(exchange, "/login");
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
