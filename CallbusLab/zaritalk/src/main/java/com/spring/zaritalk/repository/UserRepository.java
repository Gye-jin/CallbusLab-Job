package com.spring.zaritalk.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.zaritalk.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	public Optional<User> findByAccountIdAndQuitIsFalse(String accountId);
	
}
