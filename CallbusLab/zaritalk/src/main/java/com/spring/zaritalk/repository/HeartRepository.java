package com.spring.zaritalk.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.zaritalk.model.Board;
import com.spring.zaritalk.model.Heart;
import com.spring.zaritalk.model.User;

@Repository
public interface HeartRepository extends JpaRepository<Heart, Long>{
	
	
	
	public Optional<Heart> findByBoardAndUser(Board board, User user);

	public Long countByBoard(Board board);
}
