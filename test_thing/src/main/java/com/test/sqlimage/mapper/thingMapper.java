package com.test.sqlimage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.test.sqlimage.vo.cartVo;
import com.test.sqlimage.vo.thingVo;

@Mapper
public interface thingMapper {
    
    public List<thingVo> getAllInfo();

    public thingVo getInfo(String thing_id);
    
    public thingVo inputcart(String thing_id);
    
    public cartVo selectcart(String customer_id, String thing_id);
    
    public int inputcart(String customer_id, String thing_id,int num, int price, String name, String image_path);
    
    public int updatecart(String customer_id, String thing_id,int num, int price);
    
    public List<cartVo> getcartlist(String customer_id);
    
    public int deletecart(String customer_id, String thing_id);

	public void insertThing(thingVo thingVO);

	public cartVo getCartItemByThingId(String thing_id);
	
	public int insertChart(thingVo thingVO);

}
