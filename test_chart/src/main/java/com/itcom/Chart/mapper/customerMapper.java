package com.itcom.Chart.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.itcom.Chart.vo.CustomerVO;

@Mapper
public interface customerMapper {

    // 고객 ID로 고객 정보를 가져오는 메소드 (XML 매퍼에 정의됨)
    CustomerVO getCustomerById(@Param("customerId") String customerId);
}
