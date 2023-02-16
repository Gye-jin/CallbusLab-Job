package com.spring.zaritalk.controller;

import javax.servlet.http.HttpServletRequest;

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

import com.spring.zaritalk.dto.BoardDTO;
import com.spring.zaritalk.service.BoardServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(produces = "application/json")
@Slf4j
@Api(tags = "3. 게시글 컨트롤러", description = "게시글 작성, 읽기, 수정, 삭제 가능")
public class BoardController {
	
	@Autowired
	BoardServiceImpl boardService;		
	
	// 게시글 작성
	@PostMapping("/api/board")
	@ApiOperation(value = "게시글 작성")
	public ResponseEntity<?> boardWrite(HttpServletRequest request,@RequestBody BoardDTO boardDTO){


		return boardService.BoardWrite(boardDTO,request.getSession());

	}
	
	
	// 게시글 조회
	@GetMapping("/board/{boardNo}")
	@ApiOperation(value = "게시글 조회")
	public ResponseEntity<?> boardRead(@PathVariable Long boardNo) {
		log.info("{}번 게시글 조회", boardNo);
		return new ResponseEntity<BoardDTO>(boardService.BoardRead(boardNo),HttpStatus.OK);
	}
	
	
	
	// 게시글 수정
	@PutMapping("/api/board/{boardNo}")
	@ApiOperation(value = "게시글 수정")
	public ResponseEntity<?> boardUpdate(@RequestBody BoardDTO boardDTO, @PathVariable Long boardNo,
			HttpServletRequest request) {

		return boardService.updateBoard(boardDTO, boardNo, request.getSession());

	}
	// 게시글 삭제
	@DeleteMapping("/api/board/{boardNo}")
	@ApiOperation(value = "게시글 삭제")
	public ResponseEntity<?> boardDelete(@PathVariable Long boardNo, HttpServletRequest request) {

		return boardService.deleteBoard(boardNo, request.getSession());
	}
	
	

}
