package com.example.gateway.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
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

@Component
public class GlobalTokenValidationFilter implements GlobalFilter, Ordered {

    @Value("${jwt.server.url}")
    private String jwtServerUrl;

    @Value("${auth.server.url}")
    private String authServerUrl; // 리디렉션 URL 변수

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

        if (isStaticResourcePath(path) || EXCLUDED_PATHS.contains(path)) {
            System.out.println("Excluded or static path detected. Skipping filter.");
            return chain.filter(exchange);
        }

        String refreshToken = extractRefreshToken(exchange.getRequest());
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
                .onErrorResume(error -> {
                    System.out.println("Reissue or validation failed. Redirecting to login.");
                    return redirectToLogin(exchange);
                });
    }

    private boolean isStaticResourcePath(String path) {
        return STATIC_RESOURCE_PATHS.stream().anyMatch(path::startsWith);
    }

    private String extractRefreshToken(ServerHttpRequest request) {
        HttpCookie refreshTokenCookie = request.getCookies().getFirst("refreshToken");
        String token = (refreshTokenCookie != null) ? refreshTokenCookie.getValue() : null;
        System.out.println("Extracted refresh token: " + token);
        return token;
    }

    private Mono<String> reissueAccessToken(String refreshToken) {
        return webClient.post()
                .uri(jwtServerUrl + "/jwt/refresh")
                .cookies(cookies -> cookies.add("refreshToken", refreshToken))
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(token -> System.out.println("Access token reissued successfully. Token: " + token))
                .onErrorResume(error -> {
                    System.out.println("Error reissuing access token: " + error.getMessage());
                    return Mono.error(new RuntimeException("Unauthorized, redirecting to login"));
                });
    }

    private Mono<Boolean> validateAccessToken(String accessToken) {
        System.out.println("Extracted access token: " + accessToken);
        String tokenValue;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(accessToken);
            tokenValue = jsonNode.get("token").asText();
        } catch (Exception e) {
            System.out.println("Error parsing access token JSON: " + e.getMessage());
            return Mono.just(false);
        }

        return webClient.get()
                .uri(jwtServerUrl + "/jwt/validate")
                .header("Authorization", "Bearer " + tokenValue)
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

    private Mono<Void> redirectToUri(ServerWebExchange exchange, String uri) {
        System.out.println("Redirecting to URI: " + uri);

        if (exchange.getResponse().isCommitted()) {
            System.out.println("Response already committed. Cannot redirect.");
            return Mono.empty();
        }

        ServerHttpResponse response = exchange.getResponse();
        HttpHeaders headers = response.getHeaders();
        headers.setLocation(URI.create(uri));
        
        response.setStatusCode(HttpStatus.FOUND);
        return response.setComplete();
    }

    private Mono<Void> redirectToLogin(ServerWebExchange exchange) {
        return redirectToUri(exchange, authServerUrl + "/login"); // authServerUrl을 통한 리디렉션
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
