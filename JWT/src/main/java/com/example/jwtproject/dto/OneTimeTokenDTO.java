package com.example.jwtproject.dto;

public class OneTimeTokenDTO {
    private String username;
    private String oneTimeToken;
    private String clientIp;

    // Constructor
    public OneTimeTokenDTO(String username, String oneTimeToken, String clientIp) {
        this.username = username;
        this.oneTimeToken = oneTimeToken;
        this.clientIp = clientIp;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOneTimeToken() {
        return oneTimeToken;
    }

    public void setOneTimeToken(String oneTimeToken) {
        this.oneTimeToken = oneTimeToken;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }
}
