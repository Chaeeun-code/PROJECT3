package com.example.jwtproject.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.jwtproject.dto.CompanyDetails;
import com.example.jwtproject.dto.CustomerDetails;
import com.example.jwtproject.dto.LoginRequest;
import com.example.jwtproject.mapper.RefreshTokenMapper;
import com.example.jwtproject.mapper.UserMapper;
import com.example.jwtproject.model.User;
import com.example.jwtproject.security.JwtProvider;
import com.example.jwtproject.security.SecretKeyProvider;
import com.example.jwtproject.util.IPUtils;

import jakarta.servlet.http.HttpServletRequest;
import com.example.jwtproject.dto.OneTimeTokenDTO;
import com.example.jwtproject.dto.RefreshTokenDTO;
import com.example.jwtproject.mapper.OneTimeTokenMapper;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class AuthService {

    private final UserMapper userMapper;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenMapper refreshTokenMapper;
    public AuthService(SecretKeyProvider secretKeyProvider, UserMapper userMapper, JwtProvider jwtProvider,
    		HttpServletRequest request,PasswordEncoder passwordEncoder, RefreshTokenMapper refreshTokenMapper) {
        this.userMapper = userMapper;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
        this.refreshTokenMapper = refreshTokenMapper;
    }

    @Autowired
    private OneTimeTokenMapper oneTimeTokenMapper;
    
    public String generateOneTimeToken(String username, HttpServletRequest request) {
        String oneTimeToken = UUID.randomUUID().toString();
        String clientIp = IPUtils.getClientIP(request);

        // 기존의 같은 사용자 토큰 삭제
        oneTimeTokenMapper.deleteByUsername(username);

        // 새로운 토큰 저장
        OneTimeTokenDTO tokenDto = new OneTimeTokenDTO(username, oneTimeToken, clientIp);
        oneTimeTokenMapper.insertOneTimeToken(tokenDto);

        return oneTimeToken;
    }

    // 일회용 토큰 검증 (DB 조회 및 검증 후 삭제)
    public boolean validateOneTimeToken(String username, String oneTimeToken, HttpServletRequest request) {
        String clientIp = IPUtils.getClientIP(request);

        // DB에서 토큰을 조회하여 검증
        OneTimeTokenDTO tokenDto = oneTimeTokenMapper.findOneTimeToken(username, oneTimeToken, clientIp);

        if (tokenDto != null) {
            // 검증 성공 시 해당 토큰 삭제
            oneTimeTokenMapper.deleteByUsername(username);
            return true;
        }
        return false;
    }

    // 새로운 JWT 토큰 발급 (추가 사용자 정보 포함)
    public String generateJwtToken(String username) {
        // USERS 테이블에서 기본 사용자 정보 (username, role) 조회
        User user = userMapper.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        Map<String, Object> additionalClaims = new HashMap<>();
        additionalClaims.put("username", user.getUsername());
        additionalClaims.put("role", user.getRole());

        // role에 따라 customer 또는 company 정보 조회
        if ("customer".equalsIgnoreCase(user.getRole())) {
            CustomerDetails customerDetails = userMapper.findCustomerDetails(username);
            if (customerDetails != null) {
                additionalClaims.put("name", customerDetails.getName());
                additionalClaims.put("sex", customerDetails.getSex());
                additionalClaims.put("tel", customerDetails.getTel());
                additionalClaims.put("birth", customerDetails.getBirth());
                additionalClaims.put("email", customerDetails.getEmail());
                additionalClaims.put("address", customerDetails.getAddress());
            }
        } else if ("company".equalsIgnoreCase(user.getRole())) {
            CompanyDetails companyDetails = userMapper.findCompanyDetails(username);
            if (companyDetails != null) {
                additionalClaims.put("name", companyDetails.getName());
                additionalClaims.put("brn", companyDetails.getBrn());
                additionalClaims.put("tel", companyDetails.getTel());
                additionalClaims.put("email", companyDetails.getEmail());
                additionalClaims.put("address", companyDetails.getAddress());
            }
        }

        return jwtProvider.generateToken(username, additionalClaims);
    }
    
    // 사용자 인증
    public String authenticate(LoginRequest loginRequest) {
    	  // USERS 테이블에서 사용자 조회
        User user = userMapper.findByUsername(loginRequest.getUsername());
        if (user == null || !passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // 기본 정보만 담은 additionalClaims 생성
        Map<String, Object> additionalClaims = new HashMap<>();
        additionalClaims.put("username", user.getUsername());
        additionalClaims.put("role", user.getRole());

        // JWT 토큰 생성 시 additionalClaims 포함하여 호출
        return jwtProvider.generateToken(user.getUsername(), additionalClaims);
    }

    // 중복된 세션이 있는지 확인
    public boolean hasActiveSession(String username) {
        List<RefreshTokenDTO> tokens = refreshTokenMapper.findTokensByUsername(username);
        System.out.println(tokens);
        return tokens != null && !tokens.isEmpty();
    }

    // 모든 기존 세션 무효화
    public void invalidateExistingTokens(String username) {
        refreshTokenMapper.deleteTokensByUsername(username);
    }


    // 리프레시 토큰 생성
    public String generateRefreshToken(String username, String clientIp) {
        String refreshToken = jwtProvider.generateRefreshToken(username);
        return refreshToken;
    }

    // 리프레시 토큰 DB 저장
    public void saveRefreshToken(String username, String refreshToken, String ipAddress) {
    	System.out.println("saveRefreshToken() 진입완료");
        RefreshTokenDTO token = new RefreshTokenDTO();
        token.setUsername(username);
        token.setRefreshToken(refreshToken);
        token.setIpAddress(ipAddress);
        token.setExpiresAt(LocalDateTime.now().plusDays(7)); // 7일 만료
        refreshTokenMapper.insertRefreshToken(token);
        System.out.println("saveRefreshToken() 수행완료");
    }

    // 리프레시 토큰 검증
    public boolean validateRefreshToken(String refreshToken, String currentIp) {
        System.out.println("validateRefreshToken() 진입완료");
        RefreshTokenDTO token = refreshTokenMapper.findRefreshToken(refreshToken);
        if (token == null) {
            throw new RuntimeException("Invalid refresh token.");
        }

        // 생성 시간 확인 및 만료 처리
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime createdAt = token.getCreatedAt();
        if (createdAt != null && createdAt.isBefore(now.minusDays(7))) {
            refreshTokenMapper.deleteRefreshToken(refreshToken);
            throw new RuntimeException("Refresh token expired due to age.");
        }
        System.out.println("전달받은 리프레시 토큰: " + refreshToken);
        System.out.println("전달받은 ip 주소: " + currentIp);
        System.out.println("DB에서 조회된 토큰 정보: " + token.getRefreshToken());
        System.out.println("DB에서 조회된 IP 주소: " + token.getIpAddress());

        // IP와 만료 시간 검증
        if (token.getIpAddress() == null || !token.getIpAddress().equals(currentIp) ||
                token.getExpiresAt().isBefore(LocalDateTime.now())) {
            refreshTokenMapper.deleteRefreshToken(refreshToken);
            throw new RuntimeException("Refresh token expired or IP mismatch.");
        }

        return true; // 검증 성공
    }
    
    // Refresh Token 검증 및 삭제
    public boolean validateAndInvalidateRefreshToken(String refreshToken, String clientIp) {
        RefreshTokenDTO token = refreshTokenMapper.findRefreshToken(refreshToken);

        // Refresh Token과 IP가 일치하는지 확인
        if (token != null && token.getIpAddress().equals(clientIp)) {
            refreshTokenMapper.deleteRefreshToken(refreshToken); // DB에서 Refresh Token 삭제
            return true;
        }
        // IP 불일치 시 로그아웃 거부
        return false;
    }
    
    
}



