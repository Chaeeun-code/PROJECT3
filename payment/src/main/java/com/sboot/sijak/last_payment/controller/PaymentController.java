package com.sboot.sijak.last_payment.controller;



import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.sboot.sijak.last_payment.mapper.creditMapper;



@CrossOrigin(origins = "*")
@RestController
public class PaymentController {

	@Autowired
    private creditMapper dao;
	
	@PostMapping("/payment/success")
	public String handlePaymentSuccess(
	        @RequestParam String transaction_id,
	        @RequestParam String buyer_name,
	        @RequestParam String buyer_tel,
	        @RequestParam String buyer_email,
	        @RequestParam String product_quantities,
	        @RequestParam double amount,
	        @RequestParam int credits,
	        @RequestParam String from,
	        @RequestParam String customer_id) {
		
		String thing_id = null;
		int num;
		
	    JSONArray quantitiesArray = new JSONArray(product_quantities);
	    for (int i = 0; i < quantitiesArray.length(); i++) {
	        JSONObject product = quantitiesArray.getJSONObject(i);
	        thing_id = product.getString("thingId"); 
	        num = product.getInt("quantity");
	        System.out.println(from);
	        if("product".equals(from)) {
	        	dao.deletecart(customer_id, thing_id);
	        }
	        
	        dao.insertbuy(transaction_id,customer_id, thing_id,num);
	    }
	    Integer currentCredit = dao.getcredit(customer_id);
	    
	    if (credits!=0) {
		    if (currentCredit != null && currentCredit >= credits) {
	            Integer updatedCredit = currentCredit - credits; 
	            dao.updateCredit(customer_id, updatedCredit);
	            dao.insertCreditTransaction(customer_id,credits,"결제","상품 구매");
		    }
	    }
	    return "redirect:https://localhost:8080/"; 
	    }
	 
	
}