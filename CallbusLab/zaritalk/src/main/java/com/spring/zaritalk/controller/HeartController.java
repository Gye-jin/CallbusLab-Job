package com.spring.zaritalk.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.zaritalk.dto.HeartDTO;
import com.spring.zaritalk.model.User;
import com.spring.zaritalk.service.BoardServiceImpl;
import com.spring.zaritalk.service.HeartServiceImpl;

@RestController
@RequestMapping(value = "/api",produces = "application/json")
public class HeartController {
	
	@Autowired
	HeartServiceImpl heartService;
	
	@Autowired
	BoardServiceImpl boardService;
	
	@PutMapping("/{boardNo}/heart")
	public ResponseEntity<?> doheart(@PathVariable("boardNo") Long boardNo,HttpServletRequest request, @RequestBody HeartDTO heartDTO) {
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");		
		System.out.println();
		heartService.doheart(boardNo, loginUser,heartDTO.isDoHeart());
		return new ResponseEntity<String>("ok",HttpStatus.OK);
	}
	
	// 게시글의 좋아요 갯수 가져오기
	@GetMapping("/{boardNo}/heart")
	public ResponseEntity<?>  getLike(@PathVariable("boardNo") Long boardNo,HttpServletRequest request) {	
		return new ResponseEntity<Long>(heartService.getDoHeart(boardNo),HttpStatus.OK);
	}
	
	
	
}
