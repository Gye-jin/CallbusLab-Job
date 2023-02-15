package com.spring.zaritalk.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.zaritalk.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{
	
	public Optional<Comment> findByCommentNoAndDeletedDatetimeIsNull(Long commentNo);

}
