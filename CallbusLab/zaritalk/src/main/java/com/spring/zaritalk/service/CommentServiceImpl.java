package com.spring.zaritalk.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.zaritalk.common.ErrorCode;
import com.spring.zaritalk.common.exception.ApiControllerException;
import com.spring.zaritalk.dto.CommentDTO;
import com.spring.zaritalk.model.Board;
import com.spring.zaritalk.model.Comment;
import com.spring.zaritalk.model.User;
import com.spring.zaritalk.repository.BoardRepository;
import com.spring.zaritalk.repository.CommentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CommentServiceImpl implements CommentService{
	
	@Autowired
	CommentRepository commentRepository;
	
	@Autowired
	BoardRepository boardRepository;
	

	@Override
	public void CommentWrite(Long boardNo,CommentDTO commentDTO, User loginUser) {
			
		System.out.println(commentDTO.getBoardNo());
		Comment comment = Comment.DTOToEntity(commentDTO);
		Optional<Board> boardEntity = boardRepository.findByBoardNoAndDeletedDatetimeIsNull(boardNo);
		if (!boardEntity.isPresent()) {
			log.error("ApiControllerException: {}", ErrorCode.POSTS_NOT_FOUND);
			boardEntity.orElseThrow(() -> new ApiControllerException(ErrorCode.POSTS_NOT_FOUND));
		}
		Board board = boardEntity.orElseGet(Board::new);
		
		comment.updateBoard(board);
		comment.updateUser(loginUser);
		commentRepository.save(comment);
	}
		
	@Override
	public int updateComment(Long boardNo,CommentDTO commentDTO, User loginUser) {
		
		Optional<Comment> commentEntity = commentRepository.findByCommentNoAndDeletedDatetimeIsNull(commentDTO.getCommentNo());
		if (!commentEntity.isPresent()) {
			log.error("ApiControllerException: {}", ErrorCode.POSTS_NOT_FOUND);
			commentEntity.orElseThrow(() -> new ApiControllerException(ErrorCode.POSTS_NOT_FOUND));
		}
		Comment comment = commentEntity.orElseGet(Comment::new);
		
		if(comment.getBoard().getDeletedDatetime() != null) {
			return -1;
		}
		else if(comment.getUser().getAccountId().equals(loginUser.getAccountId())) {
			comment.updateContent(commentDTO.getCommentContent());
			return 1;
		}else {
			return 0;
		}
	
	}
	
	@Override
	public int deleteComment(Long boardNo,CommentDTO commentDTO, User loginUser) {
		Optional<Comment> commentEntity = commentRepository.findByCommentNoAndDeletedDatetimeIsNull(commentDTO.getCommentNo());
		System.out.println(commentEntity.orElseGet(Comment::new).getCommentContent());
		if (!commentEntity.isPresent()) {
			log.error("ApiControllerException: {}", ErrorCode.POSTS_NOT_FOUND);
			commentEntity.orElseThrow(() -> new ApiControllerException(ErrorCode.POSTS_NOT_FOUND));
		}
		Comment comment = commentEntity.orElseGet(Comment::new);
		
		
		if(comment.getUser().getAccountId().equals(loginUser.getAccountId())) {
			comment.deleteComment();
			return 1;
		}else {
			return 0;
		}
		
	}
	
}
