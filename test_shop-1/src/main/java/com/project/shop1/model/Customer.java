package com.project.shop1.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Customer {
    
	@Id
    private String customer_id;  
    private String pw;      
    private String name;    
    private String sex;      
    private String tel;     
    private String birth;   
    private String email;    
    private String address;  
    
 // Constructor
    public Customer(String customer_id, String name, String email, String phone) {
        this.customer_id = customer_id;
        this.name = name;
        this.email = email;
        this.tel = tel;
    }

    public String getCustomerId() {
        return customer_id;
    }

    public void setCustomerId(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

