package com.sboot.sijak.last_payment.Vo;

import java.sql.Timestamp;
import java.util.List;

import lombok.Data;

@Data
public class PaymentVo<ProductQuantity> {

	public PaymentVo(String customer_id2, int credits2, String string, String string2, Timestamp from) {
		
	}
	
	private String transaction_id;
    private String recipientName;
    private String address;
    private String phone;
    private String email;
    private String deliveryMemo;
    private String customer_id;
    private int finAmount;
    private int credits;
    private List<ProductQuantity> productQuantities;
}
