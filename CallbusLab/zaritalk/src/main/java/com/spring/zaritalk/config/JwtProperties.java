package com.spring.zaritalk.config;

public interface JwtProperties {
	String SECRET = "박지성"; // 우리 서버만 알고 있는 비밀값
	int EXPIRATION_TIME = 86400000; // 1일 (1/1000초)
	String TOKEN_PREFIX = "Bearer ";
	String HEADER_STRING = "Authorization";
}
