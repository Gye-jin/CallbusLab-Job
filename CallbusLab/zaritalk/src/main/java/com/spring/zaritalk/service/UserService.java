package com.spring.zaritalk.service;

import com.spring.zaritalk.common.PageResultDTO;
import com.spring.zaritalk.dto.BoardDTO;
import com.spring.zaritalk.dto.UserDTO;
import com.spring.zaritalk.model.Board;
import com.spring.zaritalk.model.User;

public interface UserService {
	
	public void joinUser(UserDTO userDTO);
	
	public void withdrawUser(User loginUser);
	
//	public void updateUser(User loginUser,UserDTO userDTO);
	
	public PageResultDTO<BoardDTO, Board> getMylist(User loginUser);
}
