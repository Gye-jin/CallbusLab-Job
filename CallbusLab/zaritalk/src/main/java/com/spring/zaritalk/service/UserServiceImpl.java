package com.spring.zaritalk.service;

import java.util.Optional;
import java.util.function.Function;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.zaritalk.common.ErrorCode;
import com.spring.zaritalk.common.ErrorResponse;
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
	public ResponseEntity<?> joinUser(UserDTO userDTO) {
		if(!userRepository.existsByAccountId(userDTO.getAccountId())) {
			User user = User.DTOToEntity(userDTO);
			user.passwordEncoding(encoder.encode(user.getUserPw()));
			userRepository.save(user);
			return new ResponseEntity<String>("ok", HttpStatus.OK);
		}else {
		    return new ResponseEntity<ErrorResponse>(new ErrorResponse(ErrorCode.CONFILICT),HttpStatus.CONFLICT);
		}

	}
		
	@Override
	@Transactional
	public ResponseEntity<?> withdrawUser(HttpSession session) {
		
		User loginUser = (User) session.getAttribute("loginUser");
		log.info("{}가 회원 탈퇴함",loginUser.getAccountId());
		
		Optional<User> userEntity = userRepository.findById(loginUser.getUserNo());
		if (!userEntity.isPresent()) {
			log.error("IllegalArgumentException: {}", userEntity);
			userEntity.orElseThrow(() -> new IllegalArgumentException());
		}
		User user = userEntity.orElseGet(User::new);
		user.withDrawUser(true);
		user.getBoards().stream().forEach(board -> board.deleteBoard());
		session.invalidate();
		
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}
	
	@Override
	@Transactional
	public PageResultDTO<BoardDTO, Board> getMylist(HttpSession session) {
		User loginUser = (User) session.getAttribute("loginUser");
		log.info("{}가 작성한 게시글 확인",loginUser.getAccountId());
		
		Pageable pageable = PageRequestDTO.builder().page(1).size(10).build().getPageable();
		Page<Board> result = boardRepository.findByUserAndDeletedDatetimeIsNull(loginUser, pageable);

		Function<Board, BoardDTO> fn = (board -> BoardDTO.EntityToDTO(board));
		
		return new PageResultDTO<BoardDTO, Board>(result, fn);
	}
	
}
