package com.example.jwtproject.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwtproject.dto.ExpireSessionRequest;
import com.example.jwtproject.dto.LoginRequest;
import com.example.jwtproject.dto.LoginResponse;
import com.example.jwtproject.security.JwtProvider;
import com.example.jwtproject.service.AuthService;
import com.example.jwtproject.util.IPUtils;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/jwt")
public class AuthController {

    private final AuthService authService;
    private final JwtProvider jwtProvider;

    public AuthController(AuthService authService, JwtProvider jwtProvider) {
        this.authService = authService;
        this.jwtProvider = jwtProvider;
    }  
      
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest,
                                               HttpServletRequest request,
                                               HttpServletResponse response) {
<<<<<<< HEAD
        System.out.println("로그인 요청 수행");

=======
    	
    	System.out.println("로그인 요청 수행");
    	
>>>>>>> origin/main
        String username = loginRequest.getUsername();
        String clientIp = IPUtils.getClientIP(request);

        try {
            // 1. 사용자 아이디와 비밀번호 검증
            authService.authenticate(loginRequest);

            // 2. 중복된 세션 확인
            if (authService.hasActiveSession(username)) {
                // 중복된 세션이 있는 경우, 일회용 토큰만 발급하고 액세스/리프레시 토큰 생성 생략
                String oneTimeToken = authService.generateOneTimeToken(username, request); // DB에 저장
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new LoginResponse(null, "이미 로그인된 사용자입니다. 기존 세션을 만료하시겠습니까?", oneTimeToken));
            }

            // 3. 중복 세션이 없는 경우에만 JWT 및 Refresh Token 생성
<<<<<<< HEAD
            String token = authService.generateJwtToken(username);  // 추가 정보 포함한 JWT 생성
=======
            String token = authService.generateJwtToken(username);
>>>>>>> origin/main
            String refreshToken = authService.generateRefreshToken(username, clientIp);

            // 리프레시 토큰을 DB에 저장
            System.out.println(username + refreshToken + clientIp);
            authService.saveRefreshToken(username, refreshToken, clientIp);

<<<<<<< HEAD
            // 클라이언트에 리프레시 토큰을 쿠키로 설정
            response.addHeader("Set-Cookie",
                    "refreshToken=" + refreshToken + "; Path=/; HttpOnly; Secure; SameSite=Lax; Max-Age=" + (7 * 24 * 60 * 60));

=======
         // 클라이언트에 리프레시 토큰을 쿠키로 설정
            response.addHeader("Set-Cookie",
                    "refreshToken=" + refreshToken + "; Path=/; HttpOnly; Secure; SameSite=Lax; Max-Age=" + (7 * 24 * 60 * 60));


>>>>>>> origin/main
            return ResponseEntity.ok(new LoginResponse(token));

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponse(null, "아이디 또는 비밀번호가 유효하지 않습니다."));
        }
    }





<<<<<<< HEAD
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(HttpServletRequest request, HttpServletResponse response) {
=======
    @PostMapping("/expire")
    public ResponseEntity<Void> expireSession(HttpServletRequest request,
                                              @RequestBody ExpireSessionRequest expireSessionRequest) {
        String oneTimeToken = expireSessionRequest.getOneTimeToken();
        String username = expireSessionRequest.getUsername();

        // 일회성 토큰을 검증
        if (authService.validateOneTimeToken(username, oneTimeToken, request)) {  // DB에서 검증
            // 기존 세션을 만료
            authService.invalidateExistingTokens(username);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .build();
        }
    }




    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(HttpServletRequest request, HttpServletResponse response) {
        // 클라이언트 IP 주소 가져오기
>>>>>>> origin/main
        String clientIp = IPUtils.getClientIP(request);
        System.out.println("클라이언트 IP: " + clientIp);

        // 쿠키에서 리프레시 토큰을 가져오기
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            System.out.println("쿠키가 없음");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token is missing");
        }

        String refreshToken = Arrays.stream(cookies)
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);

        if (refreshToken == null) {
            System.out.println("리프레시 토큰이 없음");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired refresh token");
        }

        // 리프레시 토큰이 존재하는 경우 validateRefreshToken 호출
        try {
            System.out.println("리프레시 토큰 검증 시작");
            authService.validateRefreshToken(refreshToken, clientIp);
        } catch (RuntimeException e) {
            System.out.println("리프레시 토큰 검증 실패: " + e.getMessage());
<<<<<<< HEAD
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token validation failed");
        }

        // 리프레시 토큰이 유효한 경우 추가 정보 포함하여 새로운 JWT 발급
        String username = jwtProvider.getUsernameFromToken(refreshToken);
        String newToken = authService.generateJwtToken(username);  // 추가 정보 포함한 JWT 생성
        return ResponseEntity.ok(new LoginResponse(newToken));
    }

=======
            String refTokFail="Refresh token validation failed";
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(refTokFail);
        }

        // 리프레시 토큰이 유효한 경우 새로운 JWT 발급
        String username = jwtProvider.getUsernameFromToken(refreshToken);
        String newToken = jwtProvider.generateToken(username);
        return ResponseEntity.ok(new LoginResponse(newToken));

    }
>>>>>>> origin/main

    

    
    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String bearerToken) {
        if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
            System.out.println("잘못된 토큰 형식: " + bearerToken);
            return ResponseEntity.badRequest().body("Invalid token format");
        }

        String token = bearerToken.substring(7); // "Bearer " 부분을 제거
        System.out.println("추출된 토큰: " + token);

        try {
            // 토큰 유효성 확인
            if (jwtProvider.validateToken(token)) {
                String username = jwtProvider.getUsernameFromToken(token);
                System.out.println("유효한 토큰 - 사용자 이름: " + username);

                Map<String, String> response = new HashMap<>();
                response.put("message", "Token is valid");
                response.put("username", username);

                return ResponseEntity.ok(response);
            } else {
                System.out.println("토큰이 유효하지 않거나 만료됨");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
            }
        } catch (ExpiredJwtException e) {
            System.out.println("토큰이 만료되었습니다: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is expired");
        }
    }
    
    
<<<<<<< HEAD
    @PostMapping("/expire")
    public ResponseEntity<Void> expireSession(HttpServletRequest request,
                                              @RequestBody ExpireSessionRequest expireSessionRequest) {
        String oneTimeToken = expireSessionRequest.getOneTimeToken();
        String username = expireSessionRequest.getUsername();

        // 일회성 토큰을 검증
        if (authService.validateOneTimeToken(username, oneTimeToken, request)) {  // DB에서 검증
            // 기존 세션을 만료
            authService.invalidateExistingTokens(username);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .build();
        }
    }
    
    
=======
>>>>>>> origin/main
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        String clientIp = IPUtils.getClientIP(request);
        String refreshToken = Arrays.stream(request.getCookies())
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);

        // Refresh Token이 없으면 로그아웃 실패
        if (refreshToken == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Refresh token is missing");
        }

        try {
            // Refresh Token과 IP 검증
            if (authService.validateAndInvalidateRefreshToken(refreshToken, clientIp)) {
                // 쿠키 삭제
                Cookie deleteRefreshTokenCookie = new Cookie("refreshToken", null);
                deleteRefreshTokenCookie.setHttpOnly(true);
                deleteRefreshTokenCookie.setSecure(true);
                deleteRefreshTokenCookie.setPath("/jwt");
                deleteRefreshTokenCookie.setMaxAge(0); // 즉시 삭제
                response.addCookie(deleteRefreshTokenCookie);

                return ResponseEntity.ok("로그아웃되었습니다.");
            } else {
                // IP 주소가 불일치하는 경우 처리
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid logout attempt detected.");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while logging out.");
        }
    }
}
