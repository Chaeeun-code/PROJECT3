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
<<<<<<< HEAD
//	        @RequestParam String transaction_id,
//	        @RequestParam String buyer_name,
//	        @RequestParam String buyer_tel,
//	        @RequestParam String buyer_email,
//	        @RequestParam String product_quantities,
//	        @RequestParam double amount,
//	        @RequestParam int credits,
//	        @RequestParam String from,
//	        @RequestParam String customer_id) {
			@RequestParam(name = "transaction_id") String transaction_id,
	        @RequestParam(name = "buyer_name") String buyer_name,
	        @RequestParam(name = "buyer_tel") String buyer_tel,
	        @RequestParam(name = "buyer_email") String buyer_email,
	        @RequestParam(name = "product_quantities") String product_quantities,
	        @RequestParam(name = "amount") double amount,
	        @RequestParam(name = "credits") int credits,
	        @RequestParam(name = "from") String from,
	        @RequestParam(name = "customer_id") String customer_id) {
		
		System.out.println("1");
		System.out.println("transaction_id: " + transaction_id);
        System.out.println("buyer_name: " + buyer_name);
        System.out.println("buyer_tel: " + buyer_tel);
        System.out.println("buyer_email: " + buyer_email);
        System.out.println("product_quantities: " + product_quantities);
        System.out.println("amount: " + amount);
        System.out.println("credits: " + credits);
        System.out.println("from: " + from);
        System.out.println("customer_id: " + customer_id);
		String thing_id = null;
		int num;

=======
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
		
>>>>>>> origin/main
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
<<<<<<< HEAD
	    System.out.println("2");
=======
	    
>>>>>>> origin/main
	    if (credits!=0) {
		    if (currentCredit != null && currentCredit >= credits) {
	            Integer updatedCredit = currentCredit - credits; 
	            dao.updateCredit(customer_id, updatedCredit);
	            dao.insertCreditTransaction(customer_id,credits,"결제","상품 구매");
		    }
	    }
<<<<<<< HEAD
	    return "redirect:https://localhost:8443/"; 
=======
	    return "redirect:https://localhost:8080/"; 
>>>>>>> origin/main
	    }
	 
	
}