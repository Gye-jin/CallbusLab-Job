package com.spring.zaritalk.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.spring.zaritalk.model.User;
import com.spring.zaritalk.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginUserService implements UserDetailsService{
	
	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String accountId) throws UsernameNotFoundException {
		User user = userRepository.findByAccountId(accountId);
		System.out.println(user.getAccountId());
		System.out.println(user.getUserPw());
		System.out.println("======"+accountId);
		return new LoginUser(user);
	}

}
