package com.spring.zaritalk.service;

import javax.transaction.Transactional;

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
	
	@Override
	@Transactional
	public void withdrawUser(User user) {
		User userEntity = userRepository.findById(user.getUserNo()).orElseThrow(()-> new IllegalArgumentException());
		userEntity.withDrawUser(true);
	}
	
}
