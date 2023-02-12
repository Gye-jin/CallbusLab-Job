package com.spring.zaritalk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.zaritalk.dto.UserDTO;
import com.spring.zaritalk.service.UserServiceImpl;

@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class UserController {
	
	@Autowired
	UserServiceImpl userService;
	
	@PostMapping("/join")
	public boolean join(@RequestBody UserDTO userDTO) {
		System.out.println(userDTO);
		userService.joinUser(userDTO);
		System.out.println("---------");
		return true;
	}
	
	@PostMapping("/login")
	public void login(@RequestBody UserDTO userDTO) {
		userService.login(userDTO);
	}
	
}
