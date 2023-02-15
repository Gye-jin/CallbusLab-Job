package com.spring.zaritalk.service;

import java.util.Optional;
import java.util.function.Function;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.zaritalk.common.PageRequestDTO;
import com.spring.zaritalk.common.PageResultDTO;
import com.spring.zaritalk.dto.BoardDTO;
import com.spring.zaritalk.dto.UserDTO;
import com.spring.zaritalk.model.Board;
import com.spring.zaritalk.model.User;
import com.spring.zaritalk.repository.BoardRepository;
import com.spring.zaritalk.repository.CommentRepository;
import com.spring.zaritalk.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserRepository userRepository;
	@Autowired
	BoardRepository boardRepository;
	@Autowired
	CommentRepository commentRepository;
	
	
	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
	@Override
	public void joinUser(UserDTO userDTO) {
		User user = User.DTOToEntity(userDTO);
		user.passwordEncoding(encoder.encode(user.getUserPw()));
		userRepository.save(user);
	}
	
//	@Override
//	@Transactional
//	public void updateUser(User loginUser,UserDTO userDTO) {
//		Optional<User> userEntity = userRepository.findById(loginUser.getUserNo());
//		if (!userEntity.isPresent()) {
//			log.error("IllegalArgumentException: {}", userEntity);
//			userEntity.orElseThrow(() -> new IllegalArgumentException());
//		}else {		
//		User user = userEntity.orElseGet(User::new);
//		user.updateUser(userDTO.getNickName(), userDTO.getUserPw(), userDTO.getAccountType());
//		}
//	}
	
	@Override
	@Transactional
	public void withdrawUser(User loginUser) {
		Optional<User> userEntity = userRepository.findById(loginUser.getUserNo());
		if (!userEntity.isPresent()) {
			log.error("IllegalArgumentException: {}", userEntity);
			userEntity.orElseThrow(() -> new IllegalArgumentException());
		}
		User user = userEntity.orElseGet(User::new);

		user.withDrawUser(true);

		user.getBoards().stream().forEach(board -> board.deleteBoard());
	}
	
	@Override
	@Transactional
	public PageResultDTO<BoardDTO, Board> getMylist(User loginUser) {
		Pageable pageable = PageRequestDTO.builder().page(1).size(10).build().getPageable();
		Page<Board> result = boardRepository.findByUserAndDeletedDatetimeIsNull(loginUser, pageable);
		System.out.println("#####");
		Function<Board, BoardDTO> fn = (board -> BoardDTO.EntityToDTO(board));
		
		return new PageResultDTO<BoardDTO, Board>(result, fn);
	}
	
}
