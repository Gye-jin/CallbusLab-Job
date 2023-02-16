package com.spring.zaritalk.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.spring.zaritalk.common.ErrorCode;
import com.spring.zaritalk.common.exception.ApiControllerException;
import com.spring.zaritalk.dto.HeartHistoryDTO;
import com.spring.zaritalk.model.Board;
import com.spring.zaritalk.model.Heart;
import com.spring.zaritalk.model.User;
import com.spring.zaritalk.repository.BoardRepository;
import com.spring.zaritalk.repository.HeartRepository;
import com.spring.zaritalk.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@RequiredArgsConstructor
@Slf4j
public class HeartServiceImpl implements HeartService{
	
	@Autowired
	HeartRepository heartRepository;
	@Autowired
	BoardRepository boardRepository;
	@Autowired
	UserRepository userRepotiroy;
	
	
	@Override
	@Transactional
	public ResponseEntity<?> doheart(Long boardNo, HttpSession session, boolean doheart) {

		User loginUser = (User) session.getAttribute("loginUser");

		Optional<Board> boardEntity = boardRepository.findById(boardNo);
		if (!boardEntity.isPresent()) {
			log.error("ApiControllerException: {}", ErrorCode.POSTS_NOT_FOUND);
			boardEntity.orElseThrow(() -> new ApiControllerException(ErrorCode.POSTS_NOT_FOUND));
		}
		Board board = boardEntity.orElseGet(Board::new);

		Optional<Heart> heartEntity = heartRepository.findByBoardAndUser(board, loginUser);
		log.info("{}가{}글을 {}함", loginUser.getAccountId(), boardNo, doheart);
		
		if (!heartEntity.isPresent()) {
			Heart heart = Heart.builder().board(board).doHeart(doheart).user(loginUser).build();
			heartRepository.saveAndFlush(heart);
			board.plusHeartCnt();
	
		} else {
			Heart heart = heartEntity.orElseGet(Heart::new);
			if (doheart) {
				heart.updateDoheart(doheart);
				board.plusHeartCnt();

			} else {
				heart.updateDoheart(doheart);
				board.minusHeartCnt();
	
			}

		}
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}
	
	@Override
	@Transactional
	public List<HeartHistoryDTO> getDoHeart(String accountId, HttpSession session) {

		User loginUser = (User) session.getAttribute("loginUser");		

		log.info("{}가{}좋아요 누른 내역을 조회함.",loginUser.getAccountId(),accountId);

		Optional<User> userEntity = userRepotiroy.findByAccountIdAndQuitIsFalse(accountId);
		if (!userEntity.isPresent()) {
			log.error("ApiControllerException: {}", ErrorCode.BAD_REQUEST);
			userEntity.orElseThrow(() -> new ApiControllerException(ErrorCode.BAD_REQUEST));
		}
		User user = userEntity.orElseGet(User::new);

		return user.getHearts().stream().map(heart -> HeartHistoryDTO.EntityToDTO(heart)).collect(Collectors.toList());
	}

		
	
}
