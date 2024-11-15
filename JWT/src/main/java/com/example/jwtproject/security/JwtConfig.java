package com.example.jwtproject.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.VaultResponse;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Configuration
public class JwtConfig {

    private static final Logger logger = LoggerFactory.getLogger(JwtConfig.class);

    private final VaultTemplate vaultTemplate;
    
    @Value("${vault.secret-path:secret/jwt-project}")
    private String secretPath;

    @Value("${jwt.expiration-time}")
    private long expirationTime;

    @Value("${jwt.refresh-expiration-time}")
    private long refreshExpirationTime;

    @Value("${spring.cloud.vault.token}")
    private String vaultToken;

    public JwtConfig(VaultTemplate vaultTemplate) {
        this.vaultTemplate = vaultTemplate;
    }

    @PostConstruct
    public void logVaultToken() {
        logger.info("Using Vault Token: {}", vaultToken);
    }

    @Bean
    protected JwtProvider jwtProvider() {
        String secretKeyPlainText = getSecretKeyFromVault();
        SecretKey secretKey = Keys.hmacShaKeyFor(secretKeyPlainText.getBytes(StandardCharsets.UTF_8));
        return new JwtProvider(secretKey, expirationTime, refreshExpirationTime);
    }

    private String getSecretKeyFromVault() {
        logger.info("Reading secret key from Vault at path: {}", secretPath);

        VaultResponse response = vaultTemplate.read(secretPath);

        if (response == null || response.getData() == null) {
            logger.error("No response or data found at Vault path: {}", secretPath);
            throw new IllegalStateException("No response or data found at Vault path: " + secretPath);
        }

        @SuppressWarnings("unchecked")
		Map<String, Object> data = (Map<String, Object>) response.getData().get("data");
        if (data == null || !data.containsKey("jwt-secret-key")) {
            logger.error("Secret key not found in Vault at path: {}", secretPath);
            throw new IllegalStateException("Secret key not found in Vault at path: " + secretPath);
        }

        logger.info("Successfully retrieved secret key from Vault");
        return (String) data.get("jwt-secret-key");
    }
}
