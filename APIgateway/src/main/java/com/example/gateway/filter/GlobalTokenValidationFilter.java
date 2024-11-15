package com.example.gateway.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpCookie;
<<<<<<< HEAD
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
=======
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

>>>>>>> origin/main
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import reactor.core.publisher.Mono;
import org.springframework.web.reactive.function.client.WebClient;
<<<<<<< HEAD

import java.net.URI;
import java.util.List;

@Component
public class GlobalTokenValidationFilter implements GlobalFilter, Ordered {

    @Value("${jwt.server.url}")
    private String jwtServerUrl;

    @Value("${auth.server.url}")
    private String authServerUrl; // 리디렉션 URL 변수

    private final WebClient webClient;

=======
import java.net.URI;
import java.util.List;

//@Component
public class GlobalTokenValidationFilter implements GlobalFilter, Ordered {

    @Value("${auth.server.url}")
    private String authServerUrl;

    
    
    private final WebClient webClient;
    
>>>>>>> origin/main
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

<<<<<<< HEAD
=======
        // 정적 리소스 또는 예외 경로는 필터 생략
>>>>>>> origin/main
        if (isStaticResourcePath(path) || EXCLUDED_PATHS.contains(path)) {
            System.out.println("Excluded or static path detected. Skipping filter.");
            return chain.filter(exchange);
        }

<<<<<<< HEAD
        String refreshToken = extractRefreshToken(exchange.getRequest());
=======
        // 모든 refreshToken 쿠키를 가져옴
        String refreshToken = extractRefreshToken(exchange.getRequest());

        // 리프레시 토큰이 없는 경우 로그인 페이지로 리디렉션
>>>>>>> origin/main
        if (refreshToken == null) {
            System.out.println("No refresh token found. Redirecting to login.");
            return redirectToLogin(exchange);
        }

        return reissueAccessToken(refreshToken)
                .flatMap(newAccessToken -> {
                    System.out.println("New access token issued: " + newAccessToken);
                    return validateAccessToken(newAccessToken)
<<<<<<< HEAD
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
=======
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
>>>>>>> origin/main
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(token -> System.out.println("Access token reissued successfully. Token: " + token))
                .onErrorResume(error -> {
                    System.out.println("Error reissuing access token: " + error.getMessage());
<<<<<<< HEAD
                    return Mono.error(new RuntimeException("Unauthorized, redirecting to login"));
                });
    }

    private Mono<Boolean> validateAccessToken(String accessToken) {
        System.out.println("Extracted access token: " + accessToken);
=======
                    return Mono.empty();
                });
    }

    // 발급된 액세스 토큰의 유효성을 검증하는 메서드
    private Mono<Boolean> validateAccessToken(String accessToken) {
        System.out.println("Extracted refresh token: " + accessToken);

        // JSON에서 "token" 필드 값만 추출
>>>>>>> origin/main
        String tokenValue;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(accessToken);
<<<<<<< HEAD
            tokenValue = jsonNode.get("token").asText();
        } catch (Exception e) {
            System.out.println("Error parsing access token JSON: " + e.getMessage());
            return Mono.just(false);
        }

        return webClient.get()
                .uri(jwtServerUrl + "/jwt/validate")
                .header("Authorization", "Bearer " + tokenValue)
=======
            tokenValue = jsonNode.get("token").asText();  // "token" 필드 값 추출
        } catch (Exception e) {
            System.out.println("Error parsing access token JSON: " + e.getMessage());
            return Mono.just(false);  // JSON 파싱 실패 시 유효성 검증 실패 처리
        }

        return webClient.get()
                .uri(authServerUrl + "/jwt/validate")
                .header("Authorization", "Bearer " + tokenValue)  // 추출한 token 값으로 Authorization 헤더 설정
>>>>>>> origin/main
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

<<<<<<< HEAD
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
=======
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
>>>>>>> origin/main
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
