package com.spring.zaritalk.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;

import com.spring.zaritalk.dto.HeartHistoryDTO;

public interface HeartService {
	public ResponseEntity<?> doheart(Long boardNo, HttpSession session, boolean doheart);
	
	public List<HeartHistoryDTO> getDoHeart(String accountId,HttpSession session);
	
}
