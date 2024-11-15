package com.example.jwtproject.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.VaultResponse;

import java.util.Map;

//Vault에서 비밀 키를 읽어오는 역할의 클래스
@Component
public class SecretKeyProvider {

 private final VaultTemplate vaultTemplate;

 @Value("${vault.secret-path:secret/data/jwt-project}") // Vault의 경로는 "secret/data/"를 포함
 private String secretPath;

 public SecretKeyProvider(VaultTemplate vaultTemplate) {
     this.vaultTemplate = vaultTemplate;
 }

 // Vault에서 비밀 키를 가져오는 메서드
 public String getSecretKey() {
     // Vault에서 데이터를 읽어옴
     VaultResponse response = vaultTemplate.read(secretPath);

     if (response != null) {
         Map<String, Object> data = response.getData();
         if (data != null && data.containsKey("jwt-secret-key")) {
             return (String) data.get("jwt-secret-key");
         }
     }

     // 키가 없거나 오류가 발생한 경우 null 반환
     System.err.println("Failed to retrieve secret key from Vault.");
     return null;
 }
}




