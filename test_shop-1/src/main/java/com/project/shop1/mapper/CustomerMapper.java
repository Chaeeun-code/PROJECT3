package com.project.shop1.mapper;

import java.util.List;
import com.project.shop1.model.Customer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CustomerMapper {
    
    // 모든 고객 조회
    List<Customer> findAllCustomers();
    
    // 특정 ID로 고객 조회
    Customer findCustomerById(String customer_id);

    // 뭐지? 
    String getId(String customer_id);
    
    // 고객 ID로 비밀번호를 조회하는 메서드
    String getPasswordByCustomerId(@Param("customer_id")String customer_id);
    
    
    // 변경시 
    int updateCustomerInfo (@Param("customerId") String customerId, 
    		                @Param("newName") String newName,
    		                @Param("newEmail") String newEmail,
    		                @Param("newPhone") String newPhone);
   
    
    // 비번변경시
    String getCurrentPassword(@Param("customerId") String customerId);
    int updatePassword(@Param("customerId") String customerId, 
    				   @Param("newPassword") String newPassword);
   
}
