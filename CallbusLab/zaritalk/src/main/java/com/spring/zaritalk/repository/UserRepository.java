package com.spring.zaritalk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.zaritalk.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
//	public User findbyUserId(String userId);
	
	public boolean existsByUserIdAndUserPw(String userId, String userPw);
}
