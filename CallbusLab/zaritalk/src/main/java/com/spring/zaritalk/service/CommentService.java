package com.spring.zaritalk.service;

import com.spring.zaritalk.dto.CommentDTO;
import com.spring.zaritalk.model.User;

public interface CommentService {
	
	public void CommentWrite(CommentDTO commentDTO, User loginUser);
	
	public CommentDTO CommentRead(Long commentNo);
	
	public int updateComment(CommentDTO commentDTO, Long no, User loginUser);
	
	public int deleteComment(Long commentNo, User loginUser);
}
