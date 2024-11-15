package com.test.sqlimage.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CloudinaryService {
	
	 private final Cloudinary cloudinary;
	 
	 public CloudinaryService(
		        @Value("${cloudinary.cloud_name}") String cloudName,
		        @Value("${cloudinary.api_key}") String apiKey,
		        @Value("${cloudinary.api_secret}") String apiSecret) {
		        
		        cloudinary = new Cloudinary(ObjectUtils.asMap(
		            "cloud_name", cloudName,
		            "api_key", apiKey,
		            "api_secret", apiSecret
		        ));
		    }

		    public String uploadImage(File file) throws IOException {
		        Map uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
		        return uploadResult.get("url").toString(); // 업로드된 이미지의 URL 반환
		    }

}
