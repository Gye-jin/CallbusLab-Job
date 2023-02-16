package com.spring.zaritalk.dto;

import java.time.LocalDateTime;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.spring.zaritalk.common.UserAccount;
import com.spring.zaritalk.model.User;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder


public class UserDTO {
	@ApiModelProperty(required = false,hidden = true)
	private long userNo;
	
	@ApiModelProperty(value = "비밀번호",required = true,hidden = false)
	private String userPw;
	@ApiModelProperty(value = "닉네임",required = true,hidden = false)
	private	String nickName;
	@ApiModelProperty(value = "아이디",required = true,hidden = false)
	private String accountId;
	@ApiModelProperty(value = "계정 타입",required = true,hidden = false)
	@Enumerated(EnumType.ORDINAL)
	private UserAccount accountType;
	
	@ApiModelProperty(required = false,hidden = true)
	private LocalDateTime createTime;
	@ApiModelProperty(required = false,hidden = true)
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
