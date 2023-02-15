package com.spring.zaritalk.config.auth;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.spring.zaritalk.common.ErrorCode;
import com.spring.zaritalk.model.User;
import com.spring.zaritalk.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginUserService implements UserDetailsService{
	
	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String accountId) throws UsernameNotFoundException  {
      
		Optional<User> userEntity = userRepository.findByAccountIdAndQuitIsFalse(accountId);
		

		if (!userEntity.isPresent()) {
			log.error("ApiControllerException: {}", ErrorCode.BAD_REQUEST);
			userEntity.orElseThrow(() -> new IllegalArgumentException());
		}
		
		User user = userEntity.orElseGet(User::new);
		return new LoginUser(user);
	}

}
