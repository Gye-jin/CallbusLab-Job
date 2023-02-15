package com.spring.zaritalk.service;

import java.util.Optional;
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
import com.spring.zaritalk.model.Heart;
import com.spring.zaritalk.model.User;
import com.spring.zaritalk.repository.BoardRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardServiceImpl implements BoardService{
	
	@Autowired
	BoardRepository boardRepository;
	
//	@Override
	public PageResultDTO<BoardDTO, Board> getList(PageRequestDTO requestDTO) {
		Pageable pageable = requestDTO.getPageable();
		Page<Board> result = boardRepository.findAllByDeletedDatetimeIsNull(pageable);
		
		Function<Board, BoardDTO> fn = (board -> BoardDTO.EntityToDTO(board));
		
		return new PageResultDTO<BoardDTO, Board>(result, fn);
	}
	@Override
	public PageResultDTO<BoardDTO, Board> getSearchList(String boardTitle, PageRequestDTO requestDTO) {
		Pageable pageable = requestDTO.getPageable();
		Page<Board> result = boardRepository.findAllByBoardTitleAndDeletedDatetimeIsNull(boardTitle, pageable);
		
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

		Optional<Board> boardEntity = boardRepository.findByBoardNoAndDeletedDatetimeIsNull(boardNo);
		if (!boardEntity.isPresent()) {
			log.error("ApiControllerException: {}", ErrorCode.POSTS_NOT_FOUND);
			boardEntity.orElseThrow(() -> new ApiControllerException(ErrorCode.POSTS_NOT_FOUND));
		}
		Board board = boardEntity.orElseGet(Board::new);
		for(Heart heart : board.getHearts()) {
			System.out.println(heart.getUser().getAccountId());
		}
		BoardDTO boardDTO = BoardDTO.EntityToDTO(board);

		return boardDTO;
	}
	
	@Transactional
	@Override
	public int updateBoard(BoardDTO boardDTO, Long boardNo, User loginUser) {
		Optional<Board> boardEntity = boardRepository.findByBoardNoAndDeletedDatetimeIsNull(boardNo);
		if (!boardEntity.isPresent()) {
			log.error("ApiControllerException: {}", ErrorCode.POSTS_NOT_FOUND);
			boardEntity.orElseThrow(() -> new ApiControllerException(ErrorCode.POSTS_NOT_FOUND));
		}
		Board board = boardEntity.orElseGet(Board::new);
		
		if(board.getUser().getAccountId().equals(loginUser.getAccountId())) {
			board.updateContent(boardDTO.getBoardContent());
			board.updateTitle(boardDTO.getBoardTitle());
			return 1;
		}else {
			return 0;
		}
	}
	
	
	@Transactional
	@Override
	public int deleteBoard(Long boardNo, User loginUser) {
		Optional<Board> boardEntity = boardRepository.findByBoardNoAndDeletedDatetimeIsNull(boardNo);
		if (!boardEntity.isPresent()) {
			log.error("ApiControllerException: {}", ErrorCode.POSTS_NOT_FOUND);
			boardEntity.orElseThrow(() -> new ApiControllerException(ErrorCode.POSTS_NOT_FOUND));
		}
		Board board = boardEntity.orElseGet(Board::new);
		
		
		if(board.getUser().getAccountId().equals(loginUser.getAccountId())) {
			board.deleteBoard();
			return 1;
		}else {
			return 0;
		}
	}
	
	
	
}
