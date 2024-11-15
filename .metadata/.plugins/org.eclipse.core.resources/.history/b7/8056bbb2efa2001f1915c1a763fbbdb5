package com.sboot.sijak.last_payment.mapper;



import org.apache.ibatis.annotations.Mapper;

import com.sboot.sijak.last_payment.Vo.PaymentVo;



@Mapper
public interface creditMapper {
    
	public void deletecart(String customer_id, String thing_id);

	public void insertbuy(String transaction_id, String customer_id, String thing_id, int num);

	public Integer getcredit(String customer_id);

	public void updateCredit(String customer_id, Integer updatedCredit);

	public void insertCreditTransaction(String customer_id, Integer updatedCredit, String string, String string2);
}
