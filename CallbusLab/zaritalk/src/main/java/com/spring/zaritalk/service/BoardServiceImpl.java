package com.spring.zaritalk.service;

import java.util.Optional;
import java.util.function.Function;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.spring.zaritalk.common.ErrorCode;
import com.spring.zaritalk.common.ErrorResponse;
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
	
	@Override
	public PageResultDTO<BoardDTO, Board> getList(int pageNo, int size) {
		PageRequestDTO requestDTO = PageRequestDTO.builder()
									.page(pageNo)
									.size(size)
									.build();
		if(requestDTO.getPage() == 0) {
			requestDTO.setPage(1);
		}
		if(requestDTO.getSize()==0){
			requestDTO.setSize(10);
		}
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
	public ResponseEntity<?> BoardWrite(BoardDTO boardDTO, HttpSession session) {
		
		User loginUser = (User) session.getAttribute("loginUser");
		log.info("{}가 게시글 작성", loginUser.getAccountId());
		
		Board boardEntity = Board.DTOToEntity(boardDTO);
		boardEntity.setHeart();
		boardEntity.updateUser(loginUser);
		boardRepository.save(boardEntity);	
		
		return new ResponseEntity<String>("ok",HttpStatus.CREATED);
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
	public ResponseEntity<?> updateBoard(BoardDTO boardDTO, Long boardNo, HttpSession session) {
		User loginUser = (User) session.getAttribute("loginUser");
		
		Optional<Board> boardEntity = boardRepository.findByBoardNoAndDeletedDatetimeIsNull(boardNo);
		if (!boardEntity.isPresent()) {
			log.error("ApiControllerException: {}", ErrorCode.POSTS_NOT_FOUND);
			boardEntity.orElseThrow(() -> new ApiControllerException(ErrorCode.POSTS_NOT_FOUND));
		}
		Board board = boardEntity.orElseGet(Board::new);
		
		if(board.getUser().getAccountId().equals(loginUser.getAccountId())) {
			board.updateContent(boardDTO.getBoardContent());
			board.updateTitle(boardDTO.getBoardTitle());
			return new ResponseEntity<String>("ok", HttpStatus.OK);
		}else {
			return new ResponseEntity<ErrorResponse>(new ErrorResponse(ErrorCode.FORBIDDEN),HttpStatus.FORBIDDEN);
		}
		
	
	}
	
	
	@Transactional
	@Override
	public ResponseEntity<?> deleteBoard(Long boardNo, HttpSession session) {

		User loginUser = (User) session.getAttribute("loginUser");
		
		Optional<Board> boardEntity = boardRepository.findByBoardNoAndDeletedDatetimeIsNull(boardNo);
		if (!boardEntity.isPresent()) {
			log.error("ApiControllerException: {}", ErrorCode.POSTS_NOT_FOUND);
			boardEntity.orElseThrow(() -> new ApiControllerException(ErrorCode.POSTS_NOT_FOUND));
		}
		Board board = boardEntity.orElseGet(Board::new);
		
		
		if(board.getUser().getAccountId().equals(loginUser.getAccountId())) {
			board.deleteBoard();
			return new ResponseEntity<String>("ok", HttpStatus.OK);
		}else {
		    return new ResponseEntity<ErrorResponse>(new ErrorResponse(ErrorCode.FORBIDDEN),HttpStatus.FORBIDDEN);
		}
	}
	
	
	
}
