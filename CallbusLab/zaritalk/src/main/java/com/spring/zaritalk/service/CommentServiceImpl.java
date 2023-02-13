package com.spring.zaritalk.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.zaritalk.dto.CommentDTO;
import com.spring.zaritalk.model.Board;
import com.spring.zaritalk.model.Comment;
import com.spring.zaritalk.model.User;
import com.spring.zaritalk.repository.BoardRepository;
import com.spring.zaritalk.repository.CommentRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService{
	
	@Autowired
	CommentRepository commentRepository;
	
	@Autowired
	BoardRepository boardRepository;
	

	@Override
	public void CommentWrite(CommentDTO commentDTO, User loginUser) {
			
		System.out.println(commentDTO.getBoardNo());
		Comment comment = Comment.DTOToEntity(commentDTO);
		Board boardEntity = boardRepository.findById(commentDTO.getBoardNo()).orElseThrow(()-> new IllegalArgumentException());
		comment.updateBoard(boardEntity);
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
			commentEntity.updateContent(commentDTO.getCommentContent());
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
