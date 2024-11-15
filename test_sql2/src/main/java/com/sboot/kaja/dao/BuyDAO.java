package com.sboot.kaja.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.sboot.kaja.vo.BuyVO;

@Mapper
public interface BuyDAO {
	
	List<BuyVO> findByCustomerId(String customerId);
}
