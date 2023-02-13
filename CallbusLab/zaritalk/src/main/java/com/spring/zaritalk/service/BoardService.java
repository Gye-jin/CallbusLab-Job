package com.spring.zaritalk.service;

import com.spring.zaritalk.dto.BoardDTO;
import com.spring.zaritalk.model.User;

public interface BoardService {
	
	public void BoardWrite(BoardDTO boardDTO, User user);
	
	public BoardDTO BoardRead(Long id);
	
	public int updateBoard(BoardDTO boardDTO, Long boardNo, User loginUser);
	
	public int deleteBoard(Long boardNo, User loginUser);
}
