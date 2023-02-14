package com.spring.zaritalk.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.zaritalk.common.ErrorCode;
import com.spring.zaritalk.common.exception.ApiControllerException;
import com.spring.zaritalk.model.Board;
import com.spring.zaritalk.model.Heart;
import com.spring.zaritalk.model.User;
import com.spring.zaritalk.repository.BoardRepository;
import com.spring.zaritalk.repository.HeartRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class HeartServiceImpl implements HeartService{
	
	@Autowired
	HeartRepository heartRepository;
	@Autowired
	BoardRepository boardRepository;
	
	
	@Override
	@Transactional
	public void doheart(Long boardNo, User loginUser, boolean doheart) {
		Board boardEntity = boardRepository.findById(boardNo)
				.orElseThrow(() -> new ApiControllerException(ErrorCode.POSTS_NOT_FOUND));
		Optional<Heart> heartEntity = heartRepository.findByBoardAndUser(boardEntity, loginUser);

		if (!heartEntity.isPresent()) {
			Heart heart = Heart.builder().board(boardEntity).doHeart(doheart).user(loginUser).build();
			heartRepository.saveAndFlush(heart);
			boardEntity.plusHeartCnt();
		} else {
			Heart heart = heartEntity.orElseGet(Heart::new);
			if (heart.isDoHeart()) {
				heart.updateDoheart(false);
				boardEntity.minusHeartCnt();
			} else {
				heart.updateDoheart(true);
				boardEntity.plusHeartCnt();
			}

		}

	}
	
	@Override
	@Transactional
	public Long getDoHeart(Long boardNo) {
		Board boardEntity = boardRepository.findById(boardNo).orElseThrow(()-> new ApiControllerException(ErrorCode.POSTS_NOT_FOUND));
		Long hearts = heartRepository.countByBoard(boardEntity);
			return hearts;
		}
		
	
}
