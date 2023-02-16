package com.spring.zaritalk.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.zaritalk.common.PageResultDTO;
import com.spring.zaritalk.dto.UserDTO;
import com.spring.zaritalk.model.User;
import com.spring.zaritalk.service.UserServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(produces = "application/json")
@Api(tags = "1. 유저 컨트롤러", description = "회원가입, 로그인, 로그아웃, 탈퇴 가능")
public class UserController {
	
	@Autowired
	UserServiceImpl userService;	
	
	// login => filter에서 진행
	
	// 회원 가입
	@PostMapping("/join")
	@ApiOperation(value = "회원가입")
	public ResponseEntity<?> join(@RequestBody UserDTO userDTO) {
		log.info("{}가 회원가입",userDTO.getAccountId());
		return userService.joinUser(userDTO);
	}
	
	@PostMapping("/login")
	@ApiOperation(value = "로그인")
	public ResponseEntity<?> login(@RequestBody UserDTO userDTO) {
		log.info("{}가 로그인",userDTO.getAccountId());
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}
	
	// 로그 아웃
	@GetMapping("/api/logout")
	@ApiOperation(value = "로그아웃")
	public ResponseEntity<?> logout(HttpServletRequest request){
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		log.info("{}가 로그 아웃함",loginUser.getAccountId());
		session.invalidate();
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}
	
	// 회원 탈퇴
	@DeleteMapping("/api/delete")
	@ApiOperation(value = "회원 탈퇴")
	public ResponseEntity<?> quit(HttpServletRequest request){

		return userService.withdrawUser(request.getSession());
	}
	
	// 내가 작성한 게시글 보기
	@SuppressWarnings("rawtypes")
	@GetMapping("/api/myboard")
	@ApiOperation(value = "내가 작성한 게시글 조회")
	public ResponseEntity<?> myboard(HttpServletRequest request){

		return new ResponseEntity<PageResultDTO>(userService.getMylist(request.getSession()), HttpStatus.OK);
	}
	
	
}
