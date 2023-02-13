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

import com.spring.zaritalk.dto.CommentDTO;
import com.spring.zaritalk.model.User;
import com.spring.zaritalk.service.CommentServiceImpl;

@RestController
@RequestMapping(value = "/api",produces = "application/json")
public class CommentController {
	
	@Autowired
	CommentServiceImpl commentService;
	
	@PostMapping("/comment")
	public ResponseEntity<?> commentWrite(@RequestBody CommentDTO commentDTO,HttpServletRequest request){
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		System.out.println(commentDTO.getCommentContent());
		commentService.CommentWrite(commentDTO, loginUser);
		System.out.println(commentDTO.getCommentContent());
		return new ResponseEntity<String>("ok",HttpStatus.CREATED);
	}
	
	// get진행이 안됨.(확인 필요)
	@GetMapping("/comment/{no}")
	public ResponseEntity<?> commentRead(@PathVariable Long no){
		return new ResponseEntity<CommentDTO>(commentService.CommentRead(no),HttpStatus.OK);
	}
	
	@PutMapping("/comment/{no}")
	public  ResponseEntity<?> commentUpdate(@RequestBody CommentDTO commentDTO, @PathVariable Long no,HttpServletRequest request){
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		int result = commentService.updateComment(commentDTO, no, loginUser);
		if(result == 1) {
			return new ResponseEntity<String>("ok", HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("fail", HttpStatus.FORBIDDEN);
		}
	}
	
	@DeleteMapping("/comment/{no}")
	public ResponseEntity<?> commentDelete(@PathVariable Long no,HttpServletRequest request){
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		int result = commentService.deleteComment(no,loginUser);
		if(result == 1) {
			return new ResponseEntity<String>("ok", HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("fail", HttpStatus.FORBIDDEN);
		}
	}
	
}
