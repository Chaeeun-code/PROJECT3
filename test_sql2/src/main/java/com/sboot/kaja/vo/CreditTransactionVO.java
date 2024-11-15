package com.sboot.kaja.vo;

import java.sql.Timestamp;

public class CreditTransactionVO {

    private String customerId;
    private Integer usedCredits;
    private String transactionType;
    private String description;
    private Timestamp transactionDate;
    private String endDate;
    private String startDate;
    private String userId;
    private String endDate2;
    private String startDate2;
    
    public CreditTransactionVO() {}
    
    // 생성자
    public CreditTransactionVO(String customerId, Integer usedCredits, String transactionType, String description, Timestamp transactionDate, String endDate,String startDate,String userId, String endDate2,String startDate2) {
    	super();
        this.customerId = customerId;
        this.usedCredits = usedCredits;
        this.transactionType = transactionType;
        this.description = description;
        this.transactionDate = transactionDate;
        this.endDate = endDate;
        this.startDate =startDate;
        this.userId = userId;
        this.endDate = endDate2;
        this.startDate =startDate2;
    }

    

    public String getEndDate2() {
		return endDate2;
	}

	public void setEndDate2(String endDate2) {
		this.endDate2 = endDate2;
	}

	public String getStartDate2() {
		return startDate2;
	}

	public void setStartDate2(String startDate2) {
		this.startDate2 = startDate2;
	}

	public String getEndDate() {
		return endDate;
	}



	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}



	public String getStartDate() {
		return startDate;
	}



	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}



	public String getUserId() {
		return userId;
	}



	public void setUserId(String userId) {
		this.userId = userId;
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
