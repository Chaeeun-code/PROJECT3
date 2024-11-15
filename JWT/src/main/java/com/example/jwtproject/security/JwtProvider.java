package com.example.jwtproject.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;

import javax.crypto.SecretKey;
import java.util.Date;
<<<<<<< HEAD
import java.util.Map;
=======
>>>>>>> origin/main

public class JwtProvider {

    private final SecretKey secretKey;
    private final long expirationTime;
    private final long refreshExpirationTime;

    public JwtProvider(SecretKey secretKey, long expirationTime, long refreshExpirationTime) {
        this.secretKey = secretKey;
        this.expirationTime = expirationTime;
        this.refreshExpirationTime = refreshExpirationTime;
    }

<<<<<<< HEAD
    public String generateToken(String username, Map<String, Object> additionalClaims) {
        return Jwts.builder()
                .setSubject(username)
                .addClaims(additionalClaims) // 추가된 정보들
=======
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
>>>>>>> origin/main
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    public String generateRefreshToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpirationTime))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            // 만료된 경우 예외를 던짐
            throw e;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }


    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
