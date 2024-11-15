package com.itcom.Chart.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

import com.itcom.Chart.vo.ChartVO;

@Mapper
public interface chartMapper {

    void increaseClickCount(@Param("thingId") String thingId);

    void increaseManCount(@Param("thingId") String thingId);

    void increaseWomanCount(@Param("thingId") String thingId);

    void increaseTeenagerCount(@Param("thingId") String thingId);

    void increaseMiddleAgedCount(@Param("thingId") String thingId);

    void increaseElseAgeCount(@Param("thingId") String thingId);
  
    // 추가된 메서드: 클릭 횟수가 많은 상위 4개의 상품 조회
    List<ChartVO> topClickCountItems();
    
    
    List<Map<String, Object>> getMonthlySignupCount();

    Map<String, Object> getProductDemographics(@Param("thingId") String thingId);
}
