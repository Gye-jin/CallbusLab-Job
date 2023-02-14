package com.spring.zaritalk.service;

import com.spring.zaritalk.model.User;

public interface HeartService {
	public void doheart(Long boardNo, User loginUser, boolean doheart);
	
	public Long getDoHeart(Long boardNo);
}
