package com.spring.zaritalk.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.zaritalk.dto.CommentDTO;
import com.spring.zaritalk.service.CommentServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api/board",produces = "application/json")
@Api(tags = "4. 댓글 컨트롤러", description = "댓글 작성, 수정, 삭제 가능")
public class CommentController {
	
	@Autowired
	CommentServiceImpl commentService;
	
	// 댓글 작성
	@PostMapping("/{boardNo}/comment")
	@ApiOperation(value = "댓글 작성")
	public ResponseEntity<?> commentWrite(@PathVariable Long boardNo,@RequestBody CommentDTO commentDTO,HttpServletRequest request){

	
	
		return commentService.CommentWrite(boardNo, commentDTO, request.getSession());
	}
	
	// 댓글 수정
	@PutMapping("/{boardNo}/comment")
	@ApiOperation(value = "댓글 수정")
	public  ResponseEntity<?> commentUpdate(@PathVariable Long boardNo, @RequestBody CommentDTO commentDTO, HttpServletRequest request){
	
		
		
		
		return commentService.updateComment(boardNo, commentDTO, request.getSession());
		
		
	
	}
	
	
	// 댓글 삭제
	@DeleteMapping("/{boardNo}/comment")
	@ApiOperation(value = "댓글 삭제")
	public ResponseEntity<?> commentDelete(@PathVariable Long boardNo, @RequestBody CommentDTO commentDTO, HttpServletRequest request){

		
		
	return commentService.deleteComment(boardNo, commentDTO, request.getSession());
	}
	
}
