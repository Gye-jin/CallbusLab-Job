package com.spring.zaritalk.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.beans.factory.annotation.Autowired;

import com.spring.zaritalk.model.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class UserDTO {
	private long userNo;
	private String userPw;
	private	String nickName;
	private String accountId;
	
	@Enumerated(EnumType.ORDINAL)
	private UserAccount accountType;
	
	private LocalDateTime createTime;
	
	private boolean quit;
	
//	private List<BoardDTO> boards = new ArrayList<BoardDTO>();
//	private List<HeartDTO> hearts = new ArrayList<HeartDTO>();
//	private List<CommentDTO> comments = new ArrayList<CommentDTO>();
	
	public static UserDTO EntityToDTO(User user) {
		UserDTO userDTO = UserDTO.builder()
				.userNo(user.getUserNo())
				.userPw(user.getUserPw())
				.nickName(user.getNickName())
				.accountId(user.getAccountId())
				.accountType(user.getAccountType())
				.createTime(user.getCreateTime())
				.quit(user.isQuit())
				.build();							
		return userDTO;
	}
	
}
