package com.itcom.Chart;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.itcom.Chart.vo.ChartVO;
import com.itcom.Chart.vo.CustomerVO;
import com.itcom.Chart.mapper.customerMapper;
import com.itcom.Chart.mapper.chartMapper;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class chartController {

    @Autowired
    private chartMapper chartMapper;

    @Autowired
    private customerMapper customerMapper;

    @PostMapping("/increaseClickCountAndStats")
    public ResponseEntity<String> increaseClickCountAndStats(@RequestBody Map<String, String> request) {
    	System.out.println("ㅗㅗ");
        String thing_id = request.get("thing_id");
        String customerId = request.get("customer_id");
        System.out.println(thing_id);
        if (thing_id == null || thing_id.isEmpty() || customerId == null || customerId.isEmpty()) {
            return ResponseEntity.badRequest().body("상품 ID 또는 고객 ID가 누락되었습니다.");
        }
        System.out.println(thing_id+customerId);

        try {
            System.out.println("클릭 횟수 증가 전");
            chartMapper.increaseClickCount(thing_id);
            System.out.println("클릭 횟수 증가 후");
        } catch (Exception e) {
            System.err.println("클릭 횟수 증가 중 오류 발생: " + e.getMessage());
            e.printStackTrace();  // 예외 전체 출력
        }

        
        // 고객 정보 가져오기
        CustomerVO customer = customerMapper.getCustomerById(customerId);
        System.out.println("고객 정보: " + customer);
        System.out.println("고객 생일: " + customer.getBirth());
        if (customer != null) {
            // 성별에 따라 MAN 또는 WOMAN 컬럼 업데이트
            if (customer.getSex() == 1) {
                chartMapper.increaseManCount(thing_id);
            } else {
                chartMapper.increaseWomanCount(thing_id);
            }
            
            // 나이에 따라 해당 연령대 컬럼 업데이트
            int age = calculateAge(customer.getBirth());
            if (age >= 10 && age < 20) {
                chartMapper.increaseTeenagerCount(thing_id);
            } else if (age >= 20 && age < 40) {
                chartMapper.increaseMiddleAgedCount(thing_id);
            } else {
                chartMapper.increaseElseAgeCount(thing_id);
            }
        }

        return ResponseEntity.ok("클릭 및 통계가 업데이트되었습니다.");
    }

    private int calculateAge(LocalDate birthDate) {
        if (birthDate == null) {
        	System.err.println("birthDate가 null입니다. 기본값 0을 반환합니다.");
            return 0; // 기본값
        }
        try {
            //LocalDate birth = birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate today = LocalDate.now();
            return Period.between(birthDate, today).getYears();
        } catch (Exception e) {
            System.err.println("날짜 변환 중 오류 발생: " + e.getMessage());
            return 0; // 기본값
        }
    }

    @GetMapping("/topClickCountItems")
    @ResponseBody
    public ResponseEntity<List<ChartVO>> getTopClickCountItems() {
        try {
        	
            List<ChartVO> topItems = chartMapper.topClickCountItems();
            
            return ResponseEntity.ok(topItems);
            
        } catch (Exception e) {
            System.err.println("상위 클릭 횟수 상품 조회 중 오류 발생: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/monthlySignupCount")
    public ResponseEntity<List<Map<String, Object>>> getMonthlySignupCount() {
        List<Map<String, Object>> monthlySignupCounts = chartMapper.getMonthlySignupCount();
        System.out.println("나와라"+monthlySignupCounts);
        return ResponseEntity.ok(monthlySignupCounts);
    }
    
    
    
    @GetMapping("/getProductDemographics")
    public ResponseEntity<Map<String, Object>> getProductDemographics(@RequestParam String thingId) {
        Map<String, Object> demographics = chartMapper.getProductDemographics(thingId);
        
        if (demographics == null) {
            demographics = new HashMap<>();
            demographics.put("MAN_COUNT", 0);
            demographics.put("WOMAN_COUNT", 0);
            demographics.put("TEENAGER_COUNT", 0);
            demographics.put("MIDDLEAGED_COUNT", 0);
            demographics.put("ELSEAGE_COUNT", 0);
        }
        
        System.out.println("Demographics Data: " + demographics + ", thingId: " + thingId);
        return ResponseEntity.ok(demographics);
    }


    
    
    
    
    
    
    
    }

