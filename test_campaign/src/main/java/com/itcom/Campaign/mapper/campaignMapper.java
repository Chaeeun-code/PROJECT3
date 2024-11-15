package com.itcom.Campaign.mapper;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.itcom.Campaign.vo.campaignVo;

@Mapper
public interface campaignMapper {
	
	public List<campaignVo> getAllInfo();
		
	public int insertCampaignAccept(String customerId, String campaignImage);
	
	public List<campaignVo> getCampaignAccepts(String customerId);



	public void approveCertification1(Map<String, Object> request);

	public void approveCertification(Map<String, Object> params);

}


