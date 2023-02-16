package com.spring.zaritalk.service;

import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;

import com.spring.zaritalk.dto.CommentDTO;

public interface CommentService {
	
	public ResponseEntity<?> CommentWrite(Long boardNo,CommentDTO commentDTO, HttpSession session);
		
	public ResponseEntity<?> updateComment(Long boardNo,CommentDTO commentDTO, HttpSession session);
	
	public ResponseEntity<?> deleteComment(Long boardNo,CommentDTO commentDTO, HttpSession session);
}
