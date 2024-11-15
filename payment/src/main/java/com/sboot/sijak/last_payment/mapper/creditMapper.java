package com.sboot.sijak.last_payment.mapper;



import org.apache.ibatis.annotations.Mapper;
<<<<<<< HEAD
import org.apache.ibatis.annotations.Param;
=======
>>>>>>> origin/main

import com.sboot.sijak.last_payment.Vo.PaymentVo;



<<<<<<< HEAD
//@Mapper
//public interface creditMapper {
//    
//	public void deletecart(String customer_id, String thing_id);
//
//	public void insertbuy(String transaction_id, String customer_id, String thing_id, int num);
//
//	public Integer getcredit(String customer_id);
//
//	public void updateCredit(String customer_id, Integer updatedCredit);
//
//	public void insertCreditTransaction(String customer_id, Integer updatedCredit, String string, String string2);
//}
@Mapper
public interface creditMapper {
    
    public void deletecart(
        @Param("customer_id") String customerId,
        @Param("thing_id") String thingId
    );

    public void insertbuy(
        @Param("transaction_id") String transactionId,
        @Param("customer_id") String customerId,
        @Param("thing_id") String thingId,
        @Param("num") int num
    );

    public Integer getcredit(@Param("customer_id") String customerId);

    public void updateCredit(
        @Param("customer_id") String customerId,
        @Param("updatedCredit") Integer updatedCredit
    );

    public void insertCreditTransaction(
        @Param("customer_id") String customerId,
        @Param("updatedCredit") Integer updatedCredit,
        @Param("string") String string,
        @Param("string2") String string2
    );
}
=======
@Mapper
public interface creditMapper {
    
	public void deletecart(String customer_id, String thing_id);

	public void insertbuy(String transaction_id, String customer_id, String thing_id, int num);

	public Integer getcredit(String customer_id);

	public void updateCredit(String customer_id, Integer updatedCredit);

	public void insertCreditTransaction(String customer_id, Integer updatedCredit, String string, String string2);
}
>>>>>>> origin/main
