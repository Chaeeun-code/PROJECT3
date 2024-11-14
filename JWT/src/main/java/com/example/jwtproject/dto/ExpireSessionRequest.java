package com.example.jwtproject.dto;

public class ExpireSessionRequest {
	 public String getOneTimeToken() {
		return oneTimeToken;
	}
	public void setOneTimeToken(String oneTimeToken) {
		this.oneTimeToken = oneTimeToken;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	private String oneTimeToken;
	 private String username;
}
