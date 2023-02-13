package com.spring.zaritalk.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.zaritalk.dto.CommentDTO;
import com.spring.zaritalk.model.Board;
import com.spring.zaritalk.model.Comment;
import com.spring.zaritalk.model.User;
import com.spring.zaritalk.repository.CommentRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService{
	
	@Autowired
	CommentRepository commentRepository;
	

	@Override
	public void CommentWrite(CommentDTO commentDTO, User loginUser) {
	
		Comment comment = Comment.DTOToEntity(commentDTO);
		comment.updateBoard(Board.DTOToEntity(commentDTO.getBoard()));
		comment.updateUser(loginUser);
		commentRepository.save(comment);
	}
	
	@Override
	public CommentDTO CommentRead(Long commentNo) {
		Comment comment = commentRepository.findById(commentNo).orElseThrow(()-> new IllegalArgumentException());
		CommentDTO commentDTO = CommentDTO.EntityToDTO(comment);
		return commentDTO;
	}
	
	@Override
	public int updateComment(CommentDTO commentDTO, Long commentNo, User loginUser) {
		Comment commentEntity = commentRepository.findById(commentNo).orElseThrow(()-> new IllegalArgumentException());
		
		if(commentEntity.getUser().getAccountId().equals(loginUser.getAccountId())) {
			commentEntity.updateContent(commentDTO.getContent());
			return 1;
		}else {
			return 0;
		}
	
	}
	
	@Override
	public int deleteComment(Long commentNo, User loginUser) {
		Comment commentEntity = commentRepository.findById(commentNo).orElseThrow(()-> new IllegalArgumentException());
		if(commentEntity.getUser().getAccountId().equals(loginUser.getAccountId())) {
			commentRepository.deleteById(commentNo);
			return 1;
		}else {
			return 0;
		}
		
	}
	
}
