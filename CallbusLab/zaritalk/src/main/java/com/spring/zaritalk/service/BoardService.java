package com.spring.zaritalk.service;

import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;

import com.spring.zaritalk.common.PageRequestDTO;
import com.spring.zaritalk.common.PageResultDTO;
import com.spring.zaritalk.dto.BoardDTO;
import com.spring.zaritalk.model.Board;

public interface BoardService {
	
	public PageResultDTO<BoardDTO, Board> getList(int pageNo, int size);
	
	public PageResultDTO<BoardDTO, Board> getSearchList(String boardTitle, PageRequestDTO requestDTO);
	
	public ResponseEntity<?> BoardWrite(BoardDTO boardDTO, HttpSession session);
	
	public BoardDTO BoardRead(Long id);
	
	public ResponseEntity<?> updateBoard(BoardDTO boardDTO, Long boardNo, HttpSession session);
	
	public ResponseEntity<?> deleteBoard(Long boardNo, HttpSession session);
}
