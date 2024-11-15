package com.example.jwtproject.mapper;

import java.util.List;

import org.apache.ibatis.annotations.*;

import com.example.jwtproject.dto.RefreshTokenDTO;

@Mapper
public interface RefreshTokenMapper {

	// 리프레시 토큰 삽입 (username 사용)
    @Insert("INSERT INTO refresh_tokens (username, refresh_token, ip_address, expires_at) VALUES (#{refreshToken.username}, #{refreshToken.refreshToken}, #{refreshToken.ipAddress}, #{refreshToken.expiresAt})")
    void insertRefreshToken(@Param("refreshToken") RefreshTokenDTO refreshToken);

    // 리프레시 토큰 조회
    @Select("SELECT * FROM refresh_tokens WHERE refresh_token = #{refreshToken}")
    @Results({
        @Result(column = "ip_address", property = "ipAddress"),
        @Result(column = "expires_at", property = "expiresAt")

    })
    RefreshTokenDTO findRefreshToken(@Param("refreshToken") String refreshToken);
    
    // 리프레시 토큰 조회(사용자 id 지정)
    @Select("SELECT * FROM refresh_tokens WHERE username = #{username}")
    List<RefreshTokenDTO> findTokensByUsername(@Param("username") String username);


    // 리프레시 토큰 삭제(리프레시 토큰 지정)
    @Delete("DELETE FROM refresh_tokens WHERE refresh_token = #{refreshToken}")
    void deleteRefreshToken(@Param("refreshToken") String refreshToken);
    
    // 리프레시 토큰 삭제(사용자 id 지정)
    @Delete("DELETE FROM refresh_tokens WHERE username = #{username}")
    void deleteTokensByUsername(@Param("username") String username);


}
