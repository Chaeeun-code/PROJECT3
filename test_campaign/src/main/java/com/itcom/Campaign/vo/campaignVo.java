package com.itcom.Campaign.vo;

import java.sql.Timestamp;

public class campaignVo {
    private String cimageid;
    private String cimage;
    private String customerId;          // customer_id 필드 추가
    private Timestamp certificationDate;
    private String campaignImage;// certification_date 필드 추가

    public campaignVo() {
    }

    public campaignVo(String cimageid, String cimage, String customerId, Timestamp certificationDate, String campaignImage) {
        super();
       
        this.customerId = customerId;
        this.certificationDate = certificationDate;
        this.campaignImage= campaignImage;
    }

    public String getCimageid() {
        return cimageid;
    }

    public void setCimageid(String cimageid) {
        this.cimageid = cimageid;
    }

    public String getCimage() {
        return cimage;
    }

    public void setCimage(String cimage) {
        this.cimage = cimage;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Timestamp getCertificationDate() {
        return certificationDate;
    }

    public void setCertificationDate(Timestamp certificationDate) {
        this.certificationDate = certificationDate;
    }
    
    public String getCampaignImage() {
        return campaignImage;
    }

    public void setCampaignImage(String campaignImage) {
        this.campaignImage = campaignImage;
    }

    @Override
    public String toString() {
        return "campaignVo [cimageid=" + cimageid + ", cimage=" + cimage +
               ", customerId=" + customerId + ", certificationDate=" + certificationDate + "]";
    }
}
