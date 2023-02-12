package com.spring.zaritalk.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.beans.factory.annotation.Autowired;

import com.spring.zaritalk.model.Board;
import com.spring.zaritalk.model.Heart;
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
	private	String nickname;
	private String userId;
	
	@Enumerated(EnumType.ORDINAL)
	private UserAccount accountType;
	
	private LocalDateTime createTime;
	
	private boolean quit;
	
	private List<Board> boards = new ArrayList<Board>();
	private List<Heart> hearts = new ArrayList<Heart>();
	
	public static UserDTO EntityToDTO(User user) {
		UserDTO userDTO = UserDTO.builder()
				.userNo(user.getUserNo())
				.userPw(user.getUserPw())
				.nickname(user.getNickname())
				.userId(user.getUserId())
				.accountType(user.getAccountType())
				.createTime(user.getCreateTime())
				.quit(user.isQuit())
				.boards(user.getBoards())
				.hearts(user.getHearts())
				.build();							
		return userDTO;
	}
	
}
