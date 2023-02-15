package com.spring.zaritalk.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.zaritalk.common.ErrorCode;
import com.spring.zaritalk.common.ErrorResponse;
import com.spring.zaritalk.dto.CommentDTO;
import com.spring.zaritalk.model.User;
import com.spring.zaritalk.service.CommentServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/api/board",produces = "application/json")
public class CommentController {
	
	@Autowired
	CommentServiceImpl commentService;
	
	// 댓글 작성
	@PostMapping("/{boardNo}/comment")
	public ResponseEntity<?> commentWrite(@PathVariable Long boardNo,@RequestBody CommentDTO commentDTO,HttpServletRequest request){
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		commentService.CommentWrite(boardNo, commentDTO, loginUser);
		log.info("{}가{}게시글의 댓글을 작성함.",loginUser.getAccountId(),boardNo);
		return new ResponseEntity<String>("ok",HttpStatus.CREATED);
	}
	
	// 댓글 수정
	@PutMapping("/{boardNo}/comment")
	public  ResponseEntity<?> commentUpdate(@PathVariable Long boardNo, @RequestBody CommentDTO commentDTO, HttpServletRequest request){
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		int result = commentService.updateComment(boardNo, commentDTO, loginUser);
		
		
		if(result == 1) {
			log.info("{}가{}게시글의 {}번 댓글을 수정함.",loginUser.getAccountId(),boardNo,commentDTO.getCommentNo());
			return new ResponseEntity<String>("ok", HttpStatus.OK);
		}else if(result == -1){
			log.error("{} 번 게시글은 이미 삭제 되었습니다.",boardNo);
			System.out.println(-1);
		    return new ResponseEntity<ErrorResponse>(new ErrorResponse(ErrorCode.BAD_REQUEST),HttpStatus.BAD_REQUEST);
		}else {
			log.error("접근에 제한 됨");
		    return new ResponseEntity<ErrorResponse>(new ErrorResponse(ErrorCode.FORBIDDEN),HttpStatus.FORBIDDEN);
		}
	}
	
	
	// 댓글 삭제
	@DeleteMapping("/{boardNo}/comment")
	public ResponseEntity<?> commentDelete(@PathVariable Long boardNo, @RequestBody CommentDTO commentDTO, HttpServletRequest request){
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		int result = commentService.deleteComment(boardNo, commentDTO, loginUser);
		
		
		if(result == 1) {
			log.info("{{}가{}게시글의 {}번 댓글을 삭제함.",loginUser.getAccountId(),boardNo,commentDTO.getCommentNo());
			return new ResponseEntity<String>("ok", HttpStatus.OK);
		}else {
			log.error("접근에 제한 됨");
			return new ResponseEntity<ErrorResponse>(new ErrorResponse(ErrorCode.FORBIDDEN),HttpStatus.FORBIDDEN);
		}
	}
	
}
