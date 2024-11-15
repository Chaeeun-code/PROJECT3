package com.example.jwtproject.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.jwtproject.dto.CompanyDetails;
import com.example.jwtproject.dto.CustomerDetails;
import com.example.jwtproject.model.User;

@Mapper
public interface UserMapper {

    // 사용자 이름으로 사용자 조회 쿼리 추가
    @Select("SELECT * FROM USERS WHERE username = #{username}")
    User findByUsername(@Param("username") String username);

    // CUSTOMER 테이블에서 사용자 세부 정보 조회
    @Select("SELECT name, sex, tel, birth, email, address FROM customer WHERE customer_id = #{username}")
    CustomerDetails findCustomerDetails(@Param("username") String username);

    // COMPANY 테이블에서 회사 세부 정보 조회
    @Select("SELECT name, brn, tel, email, address FROM company WHERE company_id = #{username}")
    CompanyDetails findCompanyDetails(@Param("username") String username);
    
    
    
    // 사용자 저장
    void save(User user);
}
