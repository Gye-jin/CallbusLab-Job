package com.spring.zaritalk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.zaritalk.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	public User findByAccountId(String accountId);
	
	public boolean existsByAccountIdAndUserPw(String accountId, String userPw);
}
