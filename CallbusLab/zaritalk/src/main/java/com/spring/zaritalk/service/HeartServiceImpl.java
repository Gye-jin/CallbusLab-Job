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


@Service
@RequiredArgsConstructor
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
		Board boardEntity = boardRepository.findById(boardNo)
				.orElseThrow(() -> new ApiControllerException(ErrorCode.POSTS_NOT_FOUND));
		Optional<Heart> heartEntity = heartRepository.findByBoardAndUser(boardEntity, loginUser);

		if (!heartEntity.isPresent()) {
			Heart heart = Heart.builder().board(boardEntity).doHeart(doheart).user(loginUser).build();
			heartRepository.saveAndFlush(heart);
			boardEntity.plusHeartCnt();
		} else {
			Heart heart = heartEntity.orElseGet(Heart::new);
			if (doheart) {
				heart.updateDoheart(doheart);
				System.out.println("+");
				boardEntity.plusHeartCnt();
				System.out.println(boardEntity.getHeartCnt());
			} else {
				heart.updateDoheart(doheart);
				System.out.println("-");
				boardEntity.minusHeartCnt();
				System.out.println(boardEntity.getHeartCnt());
			}

		}

	}
	
	@Override
	@Transactional
	public List<HeartHistoryDTO> getDoHeart(String accountId) {
		System.out.println("++++++++++++++++++");
		User user = userRepotiroy.findByAccountId(accountId);
		
			return user.getHearts().stream()
					.map(heart -> HeartHistoryDTO.EntityToDTO(heart))
					.collect(Collectors.toList());
		}

		
	
}
