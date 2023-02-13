package com.spring.zaritalk.service;

import com.spring.zaritalk.dto.UserDTO;
import com.spring.zaritalk.model.User;

public interface UserService {
	
	public boolean joinUser(UserDTO userDTO);
	
	public void withdrawUser(User user);
}
