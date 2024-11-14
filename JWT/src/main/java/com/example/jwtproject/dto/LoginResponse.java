package com.example.jwtproject.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginResponse {
    private String token;
	private String errorMessage;
	private String oneTimeToken;
	
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
    public LoginResponse(String token, String errorMessage, String oneTimeToken) {
        this.token = token;
        this.errorMessage = errorMessage;
        this.oneTimeToken = oneTimeToken;
    }

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
    public LoginResponse(String token) {
        this.token = token;
    }
    
    public LoginResponse(String token, String errorMessage) {
        this.token = token;
        this.errorMessage = errorMessage;
    }
    
    
    public String getOneTimeToken() {
        return oneTimeToken;
    }

    public void setOneTimeToken(String oneTimeToken) {
        this.oneTimeToken = oneTimeToken;
    }

}