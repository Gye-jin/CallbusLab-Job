package com.spring.zaritalk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.zaritalk.dto.UserDTO;
import com.spring.zaritalk.model.User;
import com.spring.zaritalk.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserRepository userRepository;
	
	
	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
	@Override
	public boolean joinUser(UserDTO userDTO) {
		User userEntity = User.DTOToEntity(userDTO);
		userEntity.passwordEncoding(encoder.encode(userEntity.getUserPw()));
		userRepository.save(userEntity);
		return true;
	}
	
	public boolean login(UserDTO userDTO) {
		User userEntity = User.DTOToEntity(userDTO);
		userEntity.passwordEncoding(userEntity.getUserPw());
		Boolean result = userRepository.existsByUserIdAndUserPw(userEntity.getUserId(), userEntity.getUserPw());

		if (result)
			return true;

		else
			return false;
	}
	
}
