package com.spring.zaritalk.service;

import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.spring.zaritalk.common.ErrorCode;
import com.spring.zaritalk.common.ErrorResponse;
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
	public ResponseEntity<?> CommentWrite(Long boardNo,CommentDTO commentDTO, HttpSession session) {
		User loginUser = (User) session.getAttribute("loginUser");
		
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
		
		return new ResponseEntity<String>("ok", HttpStatus.CREATED);
	}
		
	@Override
	public ResponseEntity<?> updateComment(Long boardNo,CommentDTO commentDTO, HttpSession session) {
		
		User loginUser = (User) session.getAttribute("loginUser");

		Optional<Comment> commentEntity = commentRepository
				.findByCommentNoAndDeletedDatetimeIsNull(commentDTO.getCommentNo());
		if (!commentEntity.isPresent()) {
			log.error("ApiControllerException: {}", ErrorCode.POSTS_NOT_FOUND);
			commentEntity.orElseThrow(() -> new ApiControllerException(ErrorCode.POSTS_NOT_FOUND));
		}
		Comment comment = commentEntity.orElseGet(Comment::new);

		if (comment.getUser().getAccountId().equals(loginUser.getAccountId())) {
			comment.updateContent(commentDTO.getCommentContent());
			log.info("{}가{}게시글의 {}번 댓글을 수정함.", loginUser.getAccountId(), boardNo, commentDTO.getCommentNo());
			return new ResponseEntity<String>("ok", HttpStatus.OK);
		} else {
			log.error("접근에 제한 됨");
			return new ResponseEntity<ErrorResponse>(new ErrorResponse(ErrorCode.FORBIDDEN), HttpStatus.FORBIDDEN);
		}
	
	}
	
	@Override
	public ResponseEntity<?> deleteComment(Long boardNo,CommentDTO commentDTO, HttpSession session) {

		User loginUser = (User) session.getAttribute("loginUser");
		Optional<Comment> commentEntity = commentRepository
				.findByCommentNoAndDeletedDatetimeIsNull(commentDTO.getCommentNo());
		
		if (!commentEntity.isPresent()) {
			log.error("ApiControllerException: {}", ErrorCode.POSTS_NOT_FOUND);
			commentEntity.orElseThrow(() -> new ApiControllerException(ErrorCode.POSTS_NOT_FOUND));
		}
		Comment comment = commentEntity.orElseGet(Comment::new);

		if (comment.getUser().getAccountId().equals(loginUser.getAccountId())) {
			comment.deleteComment();
			return new ResponseEntity<String>("ok", HttpStatus.OK);
		} else {
			return new ResponseEntity<ErrorResponse>(new ErrorResponse(ErrorCode.FORBIDDEN), HttpStatus.FORBIDDEN);
		}
		
		
		
		
	}
	
}
