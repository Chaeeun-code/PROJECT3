package com.example.jwtproject.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.jwtproject.model.User;

@Mapper
public interface UserMapper {

    // 사용자 이름으로 사용자 조회 쿼리 추가
    @Select("SELECT * FROM USERS WHERE username = #{username}")
    User findByUsername(@Param("username") String username);

    // 사용자 저장
    void save(User user);
}
