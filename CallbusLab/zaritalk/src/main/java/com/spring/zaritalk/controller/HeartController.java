package com.spring.zaritalk.controller;

import java.util.List;

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
import com.spring.zaritalk.dto.HeartHistoryDTO;
import com.spring.zaritalk.model.User;
import com.spring.zaritalk.service.BoardServiceImpl;
import com.spring.zaritalk.service.HeartServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/api",produces = "application/json")
public class HeartController {
	
	@Autowired
	HeartServiceImpl heartService;
	
	@Autowired
	BoardServiceImpl boardService;
	
	// 좋아요 기능
	@PutMapping("/{boardNo}/heart")
	public ResponseEntity<?> doheart(@PathVariable("boardNo") Long boardNo,HttpServletRequest request, @RequestBody HeartDTO heartDTO) {
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");		
		log.info("{}가{}글을 {}함",loginUser.getAccountId(),boardNo,heartDTO.isDoHeart());
		heartService.doheart(boardNo, loginUser,heartDTO.isDoHeart());
		return new ResponseEntity<String>("ok",HttpStatus.OK);
	}
	
	
	
	// user가 좋아요한 게시글 가져오기
	@GetMapping("/heart/{accountId}")
	public ResponseEntity<?>  getLike(@PathVariable("accountId") String accountId,HttpServletRequest request) {	
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");		
		log.info("{}가{}좋아요 누른 내역을 조회함.",loginUser.getAccountId(),accountId);
		return new ResponseEntity<List<HeartHistoryDTO>>(heartService.getDoHeart(accountId),HttpStatus.OK);
	}
	
	
	
}
