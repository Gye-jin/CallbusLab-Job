package com.spring.zaritalk.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.zaritalk.dto.UserDTO;
import com.spring.zaritalk.model.User;
import com.spring.zaritalk.service.UserServiceImpl;

@RestController
@RequestMapping(produces = "application/json")
public class UserController {
	
	@Autowired
	UserServiceImpl userService;	
	
	// login => filter에서 허용 가능
	
	@PostMapping("/join")
	public ResponseEntity<?> join(@RequestBody UserDTO userDTO) {
		userService.joinUser(userDTO);
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}
	
	@GetMapping("/api/logout")
	public ResponseEntity<?> logout(HttpServletRequest request){
		HttpSession session = request.getSession();
		session.invalidate();
		System.out.println("logout");
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}
	
	
	// true값일 경우 필터로 걸러내는 작업 필요함.
	@PutMapping("/api/delete")
	public ResponseEntity<?> update(HttpServletRequest request){
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		userService.withdrawUser(loginUser);
		session.invalidate();
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}
	
	
	
}
