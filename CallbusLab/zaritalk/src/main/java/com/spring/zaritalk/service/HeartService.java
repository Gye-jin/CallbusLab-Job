package com.spring.zaritalk.service;

import java.util.List;

import com.spring.zaritalk.dto.HeartHistoryDTO;
import com.spring.zaritalk.model.User;

public interface HeartService {
	public void doheart(Long boardNo, User loginUser, boolean doheart);
	
	public List<HeartHistoryDTO> getDoHeart(String accountId);
	
}
