package com.sboot.kaja.vo;

import java.util.Date;

public class BuyVO {
	private String customerId;
	private String companyId;
	private String thingId;
	private int num;
	private int price;
	private Date date_;
	
	
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getThingId() {
		return thingId;
	}
	public void setThingId(String thingId) {
		this.thingId = thingId;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public Date getDate_() {
		return date_;
	}
	public void setDate_(Date date_) {
		this.date_ = date_;
	}

}
