package com.sboot.kaja.vo;

import java.sql.Timestamp;

public class CreditSearchVO {

    private String customerId;
    private Integer usedCredits;
    private String transactionType;
    private String description;
    private Timestamp transactionDate;

    public CreditSearchVO() {}
    
    // 생성자
    public CreditSearchVO(String customerId, Integer usedCredits, String transactionType, String description, Timestamp transactionDate) {
    	super();
        this.customerId = customerId;
        this.usedCredits = usedCredits;
        this.transactionType = transactionType;
        this.description = description;
        this.transactionDate = transactionDate;

    }

    

 


	public void setTransactionDate(Timestamp transactionDate) {
		this.transactionDate = transactionDate;
	}



	// Getter와 Setter
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Integer getUsedCredits() {
        return usedCredits;
    }

    public void setUsedCredits(Integer usedCredits) {
        this.usedCredits = usedCredits;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getTransactionDate() {
        return transactionDate;
    }

   

    // toString() 메서드 (디버깅 시 유용)
    @Override
    public String toString() {
        return "CreditTransactionVO [customerId=" + customerId + ", usedCredits=" + usedCredits 
               + ", transactionType=" + transactionType + ", description=" + description 
               + ", transactionDate=" + transactionDate + "]";
    }
}
