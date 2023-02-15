package com.spring.zaritalk.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.zaritalk.model.Board;
import com.spring.zaritalk.model.User;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long>{
	
	public Page<Board> findAllByDeletedDatetimeIsNull(Pageable pageable);
	
	public Page<Board> findAllByBoardTitleAndDeletedDatetimeIsNull(String boardTitle, Pageable pageable);
	
	public Page<Board> findByUserAndDeletedDatetimeIsNull(User user, Pageable pageable);

	public Optional<Board> findByBoardNoAndDeletedDatetimeIsNull(Long boardNo);

}
