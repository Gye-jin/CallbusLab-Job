package com.spring.zaritalk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.zaritalk.common.PageRequestDTO;
import com.spring.zaritalk.common.PageResultDTO;
import com.spring.zaritalk.service.BoardServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(produces = "application/json")
@Slf4j
@Api(tags = "2. 게시글 목록 컨트롤러", description = "게시글 전체 보기")
public class BoardListController {
	
	@Autowired
	BoardServiceImpl boardService;	
	
	
	// 페이지 네이션을 이용한 페이지별 게시글 조회
	@SuppressWarnings("rawtypes")
	@GetMapping("/page/{pageNo}/{size}")
	@ApiOperation(value = "게시글 목록 조회")
	@ApiParam
	public ResponseEntity<?> boardPage(@PathVariable int pageNo, @PathVariable int size) {
		log.info("페이지네이션 접근");
		return new ResponseEntity<PageResultDTO>(boardService.getList(pageNo,size), HttpStatus.OK);
	}
	
	
	// 검색 기능을 이용한 검색 진행
	@SuppressWarnings("rawtypes")
	@GetMapping("/page/{pageNo}/{size}/{boardTitle}")
	@ApiOperation(value = "게시글 목록 검색")
	public ResponseEntity<?> boardSearch(@PathVariable String boardTitle,@PathVariable int pageNo, @PathVariable int size) {
		log.info("{} 검색 진행",boardTitle);
		PageRequestDTO requestDTO = PageRequestDTO.builder().page(pageNo).size(size).build();
		return new ResponseEntity<PageResultDTO>(boardService.getSearchList(boardTitle,requestDTO), HttpStatus.OK);
	}
}
