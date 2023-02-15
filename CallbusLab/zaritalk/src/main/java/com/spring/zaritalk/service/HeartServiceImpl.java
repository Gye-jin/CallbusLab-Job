package com.spring.zaritalk.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
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
	public void doheart(Long boardNo, User loginUser, boolean doheart) {
		
		Optional<Board> boardEntity = boardRepository.findById(boardNo);
		if (!boardEntity.isPresent()) {
			log.error("ApiControllerException: {}", ErrorCode.POSTS_NOT_FOUND);
			boardEntity.orElseThrow(() -> new ApiControllerException(ErrorCode.POSTS_NOT_FOUND));
		}
		Board board = boardEntity.orElseGet(Board::new);
		
		Optional<Heart> heartEntity = heartRepository.findByBoardAndUser(board, loginUser);

		if (!heartEntity.isPresent()) {
			Heart heart = Heart.builder().board(board).doHeart(doheart).user(loginUser).build();
			heartRepository.saveAndFlush(heart);
			board.plusHeartCnt();
		} else {
			Heart heart = heartEntity.orElseGet(Heart::new);
			if (doheart) {
				heart.updateDoheart(doheart);
				board.plusHeartCnt();
				System.out.println(board.getHeartCnt());
			} else {
				heart.updateDoheart(doheart);
				board.minusHeartCnt();
				System.out.println(board.getHeartCnt());
			}

		}

	}
	
	@Override
	@Transactional
	public List<HeartHistoryDTO> getDoHeart(String accountId) {
		System.out.println("++++++++++++++++++");
		Optional<User> userEntity = userRepotiroy.findByAccountIdAndQuitIsFalse(accountId);
		if (!userEntity.isPresent()) {
			log.error("ApiControllerException: {}", ErrorCode.BAD_REQUEST);
			userEntity.orElseThrow(() -> new ApiControllerException(ErrorCode.BAD_REQUEST));
		}
		User user = userEntity.orElseGet(User::new);

		return user.getHearts().stream().map(heart -> HeartHistoryDTO.EntityToDTO(heart)).collect(Collectors.toList());
	}

		
	
}
