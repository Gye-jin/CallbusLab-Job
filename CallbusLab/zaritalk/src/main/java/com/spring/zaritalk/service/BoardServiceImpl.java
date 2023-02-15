package com.spring.zaritalk.service;

import java.util.function.Function;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.spring.zaritalk.common.ErrorCode;
import com.spring.zaritalk.common.PageRequestDTO;
import com.spring.zaritalk.common.PageResultDTO;
import com.spring.zaritalk.common.exception.ApiControllerException;
import com.spring.zaritalk.dto.BoardDTO;
import com.spring.zaritalk.model.Board;
import com.spring.zaritalk.model.User;
import com.spring.zaritalk.repository.BoardRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{
	
	@Autowired
	BoardRepository boardRepository;
	
	@Override
	public PageResultDTO<BoardDTO, Board> getList(PageRequestDTO requestDTO) {
		Pageable pageable = requestDTO.getPageable();
		Page<Board> result = boardRepository.findAll(pageable);
		
		Function<Board, BoardDTO> fn = (board -> BoardDTO.EntityToDTO(board));
		
		return new PageResultDTO<BoardDTO, Board>(result, fn);
	}
	@Override
	public PageResultDTO<BoardDTO, Board> getSearchList(String boardTitle, PageRequestDTO requestDTO) {
		Pageable pageable = requestDTO.getPageable();
		Page<Board> result = boardRepository.findAllByBoardTitle(boardTitle, pageable);
		
		Function<Board, BoardDTO> fn = (board -> BoardDTO.EntityToDTO(board));
		
		return new PageResultDTO<BoardDTO, Board>(result, fn);
	}
	
	
	@Transactional
	@Override
	public void BoardWrite(BoardDTO boardDTO, User loginUser) {
		
		Board boardEntity = Board.DTOToEntity(boardDTO);
		boardEntity.setHeart();
		boardEntity.updateUser(loginUser);
		boardRepository.save(boardEntity);	
	}
	
	@Override
	public BoardDTO BoardRead(Long boardNo) {

		Board board = boardRepository.findById(boardNo).orElseThrow(()-> new ApiControllerException(ErrorCode.POSTS_NOT_FOUND));
		BoardDTO boardDTO = BoardDTO.EntityToDTO(board);
		System.out.println(boardDTO);
		return boardDTO;
	}
	
	@Transactional
	@Override
	public int updateBoard(BoardDTO boardDTO, Long boardNo, User loginUser) {
		Board boardEntity = boardRepository.findById(boardNo).orElseThrow(()-> new ApiControllerException(ErrorCode.POSTS_NOT_FOUND));
		
		if(boardEntity.getUser().getAccountId().equals(loginUser.getAccountId())) {
			boardEntity.updateContent(boardDTO.getBoardContent());
			boardEntity.updateTitle(boardDTO.getBoardTitle());
			return 1;
		}else {
			return 0;
		}
	}
	
	
	@Transactional
	@Override
	public int deleteBoard(Long boardNo, User loginUser) {
		Board boardEntity = boardRepository.findById(boardNo).orElseThrow(()-> new ApiControllerException(ErrorCode.POSTS_NOT_FOUND));
		
		if(boardEntity.getUser().getAccountId().equals(loginUser.getAccountId())) {
			boardRepository.deleteById(boardNo);
			return 1;
		}else {
			return 0;
		}
	}
	
	
	
}
