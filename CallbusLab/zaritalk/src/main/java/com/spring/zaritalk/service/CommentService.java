package com.spring.zaritalk.service;

import com.spring.zaritalk.dto.CommentDTO;
import com.spring.zaritalk.model.User;

public interface CommentService {
	
	public void CommentWrite(Long boardNo,CommentDTO commentDTO, User loginUser);
		
	public int updateComment(Long boardNo,CommentDTO commentDTO, User loginUser);
	
	public int deleteComment(Long boardNo,CommentDTO commentDTO, User loginUser);
}
