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

import com.spring.zaritalk.dto.BoardDTO;
import com.spring.zaritalk.model.User;
import com.spring.zaritalk.service.BoardServiceImpl;

@RestController
@RequestMapping(value = "/api",produces = "application/json")
public class BoardController {
	
	@Autowired
	BoardServiceImpl boardService;	
	
	@PostMapping("/board")
	public ResponseEntity<?> boardWrite(HttpServletRequest request,@RequestBody BoardDTO boardDTO){
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		boardService.BoardWrite(boardDTO,loginUser);
		return new ResponseEntity<String>("ok",HttpStatus.CREATED);
	}
	
	// get진행이 안됨.(확인 필요)
	@GetMapping("/board/{no}")
	public ResponseEntity<?> boardRead(@PathVariable Long no) {
		return new ResponseEntity<BoardDTO>(boardService.BoardRead(no),HttpStatus.OK);
	}
	
	
	@PutMapping("/board/{no}")
	public  ResponseEntity<?> boardUpdate(@RequestBody BoardDTO boardDTO, @PathVariable Long no,HttpServletRequest request){
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		int result = boardService.updateBoard(boardDTO, no, loginUser);
		if(result == 1) {
			return new ResponseEntity<String>("ok", HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("fail", HttpStatus.FORBIDDEN);
		}
	}
	
	@DeleteMapping("/board/{no}")
	public ResponseEntity<?> boardDelete(@PathVariable Long no,HttpServletRequest request){
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		int result = boardService.deleteBoard(no,loginUser);
		
		if(result == 1) {
			return new ResponseEntity<String>("ok", HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("fail", HttpStatus.FORBIDDEN);
		}
	}

}
