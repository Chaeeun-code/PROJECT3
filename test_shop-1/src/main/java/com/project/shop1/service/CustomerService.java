package com.project.shop1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.shop1.mapper.CustomerMapper;
import com.project.shop1.model.Customer;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    public List<Customer> getAllCustomers() {
        return customerMapper.findAllCustomers();
    }

    public Customer getCustomerById(String customer_id) {
        return customerMapper.findCustomerById(customer_id);
    }

    
    
    public boolean updateCustomerInfo(String customerId, String newName, String newEmail, String newPhone) {
        try {
            int result =  customerMapper.updateCustomerInfo(customerId, newName, newEmail, newPhone); 
            if (result > 0) {
            	return true;
            }else {
            	System.out.println("업데이트 실패");
            	return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    public boolean verifyPassword(String customer_id, String currentPassword) {
        String storedPassword = customerMapper.getPasswordByCustomerId(customer_id);
        return storedPassword != null && storedPassword.equals(currentPassword);
    }
    
    
    
    
    
 // 현재 비밀번호를 검증하는 메서드
    public boolean validateCurrentPassword(String customerId, String currentPassword) {
        String storedPassword = customerMapper.getCurrentPassword(customerId);
        return storedPassword != null && storedPassword.equals(currentPassword);
    }
  
 // 비밀번호를 업데이트하는 메서드
    public boolean updatePassword(String customerId, String newPassword) {
        int updatedRows = customerMapper.updatePassword(customerId, newPassword);
        return updatedRows > 0;
    }
    
    
    
}

