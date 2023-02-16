package com.spring.zaritalk.service;

import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;

import com.spring.zaritalk.common.PageResultDTO;
import com.spring.zaritalk.dto.BoardDTO;
import com.spring.zaritalk.dto.UserDTO;
import com.spring.zaritalk.model.Board;

public interface UserService {
	
	public ResponseEntity<?> joinUser(UserDTO userDTO);
	
	public ResponseEntity<?> withdrawUser(HttpSession session);
	
//	public void updateUser(User loginUser,UserDTO userDTO);
	
	public PageResultDTO<BoardDTO, Board> getMylist(HttpSession session);
}
