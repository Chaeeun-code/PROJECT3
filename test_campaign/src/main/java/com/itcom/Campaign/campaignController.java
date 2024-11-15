package com.itcom.Campaign;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

import com.cloudinary.utils.ObjectUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.itcom.Campaign.mapper.campaignMapper;
import com.itcom.Campaign.vo.campaignVo;


@CrossOrigin(origins = "*") // 모든 도메인 허용
@RestController
@RequestMapping("/api")
public class campaignController {
	
	@Autowired
	campaignMapper dao;
	
	@GetMapping("/posters/list")
	public List<campaignVo> getAllPosters() {
		System.out.println("여기까지는ㅇ 왔냐?");
		List<campaignVo> posters = dao.getAllInfo();
		
		if (posters == null || posters.isEmpty()) {
	        System.out.println("포스터 데이터가 비어 있습니다.");
	    } else {
	        posters.forEach(poster -> System.out.println("포스터: " + poster));
	    }
		
		return posters;
	}
	
	@GetMapping("/campaign/certification")
	public ResponseEntity<List<campaignVo>> getCampaignAccepts(@RequestParam("customer_id") String customerId) {
	    List<campaignVo> certificationList = dao.getCampaignAccepts(customerId);
	    return new ResponseEntity<>(certificationList, HttpStatus.OK);
	}


	
	
    
    
	@PostMapping("/campaign/accept")
	public ResponseEntity<Map<String, Object>> acceptCampaign(
	    @RequestParam("customer_id") String customerId,
	    @RequestParam("uploadFile") MultipartFile uploadFile) {

	    Map<String, Object> response = new HashMap<>();
	    
	    // Cloudinary 설정
	    Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
	        "cloud_name", "dhuybxduy",
	        "api_key", "324727897453721",
	        "api_secret", "rJaNQVsUL-80csaqHFinKdKUii0"
	    ));

	    String imagePath = null;
	    try {
	        File tempFile = File.createTempFile("upload-", uploadFile.getOriginalFilename());
	        uploadFile.transferTo(tempFile);
	        
	        Map<?, ?> uploadResult = cloudinary.uploader().upload(tempFile, ObjectUtils.emptyMap());
	        imagePath = (String) uploadResult.get("url");
	        
	        tempFile.delete();

	        // 데이터베이스에 customerId와 imagePath만 전달
	        int rowsAffected = dao.insertCampaignAccept(customerId, imagePath);
	        if (rowsAffected > 0) {
	            response.put("success", true);
	            response.put("message", "캠페인 참여가 성공적으로 등록되었습니다.");
	        } else {
	            response.put("success", false);
	            response.put("message", "캠페인 참여 등록에 실패했습니다.");
	        }
	        
	        return new ResponseEntity<>(response, HttpStatus.OK);

	    } catch (IOException e) {
	        e.printStackTrace();
	        response.put("success", false);
	        response.put("message", "파일 업로드에 실패했습니다: " + e.getMessage());
	        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	@PostMapping("/campaign/certification")
	public ResponseEntity<Map<String, Object>> approveCertification(@RequestBody Map<String, Object> request) {
		System.out.println("ㅅㅂ");
		String customerId = (String) request.get("customerId");
	    String certificationDateStr = (String) request.get("certificationDate");
System.out.println("ㅅㅂ1");
	    // 인증 날짜 문자열을 Instant로 변환 후, 서울 시간으로 변환
	    Instant instant = Instant.parse(certificationDateStr);
	    ZonedDateTime seoulZonedDateTime = instant.atZone(ZoneId.of("Asia/Seoul"));
	    Timestamp certificationDate = Timestamp.from(seoulZonedDateTime.toInstant());
	    System.out.println("ㅅㅂ2");
	    // 인증 내역 승인 처리
	    Map<String, Object> params = new HashMap<>();
	    params.put("customerId", customerId);
	    params.put("certificationDate", certificationDate); // Timestamp 객체 사용
	    dao.approveCertification(params); 
	    System.out.println("ㅅㅂ3");
	    dao.approveCertification(params); // DAO 호출
	    System.out.println("ㅅㅂ4");
	    Map<String, Object> response = new HashMap<>();
	    response.put("success", true);
	    response.put("message", "인증이 승인되었습니다.");
	    return ResponseEntity.ok(response);
	}


	}

