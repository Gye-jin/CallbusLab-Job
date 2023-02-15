package com.spring.zaritalk.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.zaritalk.model.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long>{
	
	public Page<Board> findAllByBoardTitle(String boardTitle, Pageable pageable);


}
