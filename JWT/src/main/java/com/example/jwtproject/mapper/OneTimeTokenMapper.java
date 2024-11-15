package com.example.jwtproject.mapper;

import com.example.jwtproject.dto.OneTimeTokenDTO;
import org.apache.ibatis.annotations.*;

@Mapper
public interface OneTimeTokenMapper {

    // 기존 토큰 삭제 (username 기준)
    @Delete("DELETE FROM one_time_tokens WHERE username = #{username}")
    void deleteByUsername(@Param("username") String username);

    // 새로운 일회용 토큰 저장
    @Insert("INSERT INTO one_time_tokens (username, one_time_token, client_ip) VALUES (#{username}, #{oneTimeToken}, #{clientIp})")
    void insertOneTimeToken(OneTimeTokenDTO tokenDto);

    // 토큰 검증을 위한 조회
    @Select("SELECT * FROM one_time_tokens WHERE username = #{username} AND one_time_token = #{oneTimeToken} AND client_ip = #{clientIp}")
    OneTimeTokenDTO findOneTimeToken(@Param("username") String username, @Param("oneTimeToken") String oneTimeToken, @Param("clientIp") String clientIp);
}
