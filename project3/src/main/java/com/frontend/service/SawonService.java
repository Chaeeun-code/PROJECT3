package com.frontend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SawonService {

    @Autowired
    private RestTemplate restTemplate;

    public String getSawonInfo(String id) {
        // Eureka를 통해 등록된 백엔드 서비스 이름으로 API 호출
        String url = "https://test-sql-service/api/sawon/info/" + id;
        return restTemplate.getForObject(url, String.class);
    }
}
