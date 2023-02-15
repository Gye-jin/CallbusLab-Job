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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(produces = "application/json")
public class UserController {
	
	@Autowired
	UserServiceImpl userService;	
	
	// login => filter에서 진행
	
	// 회원 가입
	@PostMapping("/join")
	public ResponseEntity<?> join(@RequestBody UserDTO userDTO) {
		userService.joinUser(userDTO);
		log.info("{}가 회원가입",userDTO.getAccountId());
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}
	
	// 로그 아웃
	@GetMapping("/api/logout")
	public ResponseEntity<?> logout(HttpServletRequest request){
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		log.info("{}가 로그 아웃함",loginUser.getAccountId());
		session.invalidate();
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}
//	// 회원정보 수정 불가함 세션때문에
//	@PutMapping("/api/update")
//	public ResponseEntity<?> update(HttpServletRequest request, UserDTO userDTO){
//		HttpSession session = request.getSession();
//		User loginUser = (User) session.getAttribute("loginUser");
//		userService.updateUser(loginUser,userDTO);
//		log.info("{}가 회원정보 수정함",loginUser.getAccountId());
//		session.invalidate();
//		return new ResponseEntity<String>("ok", HttpStatus.OK);
//	}
	
	// 회원 탈퇴
	@DeleteMapping("/api/delete")
	public ResponseEntity<?> quit(HttpServletRequest request){
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		log.info("{}가 회원 탈퇴함",loginUser.getAccountId());
		userService.withdrawUser(loginUser);
		session.invalidate();
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}
	
	// 내가 작성한 게시글 보기
	@SuppressWarnings("rawtypes")
	@GetMapping("/api/myboard")
	public ResponseEntity<?> myboard(HttpServletRequest request){
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		log.info("{}가 작성한 게시글 확인",loginUser.getAccountId());
		System.out.println(loginUser.getAccountId());
		return new ResponseEntity<PageResultDTO>(userService.getMylist(loginUser), HttpStatus.OK);
	}
	
	
}
