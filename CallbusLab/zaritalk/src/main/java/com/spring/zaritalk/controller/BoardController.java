package com.spring.zaritalk.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.zaritalk.common.ErrorCode;
import com.spring.zaritalk.common.ErrorResponse;
import com.spring.zaritalk.common.PageRequestDTO;
import com.spring.zaritalk.common.PageResultDTO;
import com.spring.zaritalk.dto.BoardDTO;
import com.spring.zaritalk.model.User;
import com.spring.zaritalk.service.BoardServiceImpl;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(produces = "application/json")
@Slf4j
public class BoardController {
	
	@Autowired
	BoardServiceImpl boardService;	
	
	
	// 페이지 네이션을 이용한 페이지별 게시글 조회
	@SuppressWarnings("rawtypes")
	@GetMapping("/page/{pageNo}/{size}")
	public ResponseEntity<?> boardPage(@PathVariable int pageNo, @PathVariable int size) {
		log.info("페이지네이션 접근");
		PageRequestDTO requestDTO = PageRequestDTO.builder().page(pageNo).size(size).build();
		return new ResponseEntity<PageResultDTO>(boardService.getList(requestDTO), HttpStatus.OK);
	}
	
	
	// 검색 기능을 이용한 검색 진행
	@SuppressWarnings("rawtypes")
	@GetMapping("/page/{pageNo}/{size}/{boardTitle}")
	public ResponseEntity<?> boardSearch(@PathVariable String boardTitle,@PathVariable int pageNo, @PathVariable int size) {
		log.info("{} 검색 진행",boardTitle);
		PageRequestDTO requestDTO = PageRequestDTO.builder().page(pageNo).size(size).build();
		return new ResponseEntity<PageResultDTO>(boardService.getSearchList(boardTitle,requestDTO), HttpStatus.OK);
	}
	
	
	// 게시글 작성
	@PostMapping("/api/board")
	public ResponseEntity<?> boardWrite(HttpServletRequest request,@RequestBody BoardDTO boardDTO){
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		log.info("{}가 게시글 작성", loginUser.getAccountId());
		boardService.BoardWrite(boardDTO,loginUser);
		return new ResponseEntity<String>("ok",HttpStatus.CREATED);
	}
	
	
	// 게시글 조회
	@GetMapping("/board/{boardNo}")
	public ResponseEntity<?> boardRead(@PathVariable Long boardNo) {
		log.info("{}번 게시글 조회", boardNo);
		return new ResponseEntity<BoardDTO>(boardService.BoardRead(boardNo),HttpStatus.OK);
	}
	
	
	
	// 게시글 수정
	@PutMapping("/api/board/{boardNo}")
	public  ResponseEntity<?> boardUpdate(@RequestBody BoardDTO boardDTO, @PathVariable Long boardNo,HttpServletRequest request){
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		int result = boardService.updateBoard(boardDTO, boardNo, loginUser);
		log.info("{}가 {}번 게시글 수정 시도", loginUser.getAccountId(),boardNo);
		if(result == 1) {
			return new ResponseEntity<String>("ok", HttpStatus.OK);
		}else {
			return new ResponseEntity<ErrorResponse>(new ErrorResponse(ErrorCode.FORBIDDEN),HttpStatus.FORBIDDEN);
		}
	}
	// 게시글 삭제
	@DeleteMapping("/api/board/{boardNo}")
	public ResponseEntity<?> boardDelete(@PathVariable Long boardNo,HttpServletRequest request){
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		int result = boardService.deleteBoard(boardNo,loginUser);
		
		if(result == 1) {
			return new ResponseEntity<String>("ok", HttpStatus.OK);
		}else {
		    return new ResponseEntity<ErrorResponse>(new ErrorResponse(ErrorCode.FORBIDDEN),HttpStatus.FORBIDDEN);
	    }
			
	}
	
	

}
