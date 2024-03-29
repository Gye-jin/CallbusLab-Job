package com.spring.zaritalk.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.zaritalk.common.UserAccount;
import com.spring.zaritalk.dto.UserDTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)	
@Getter
@ToString(exclude = {"boards", "comments"})
@Builder
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_no")
	private long userNo;
	@Column(nullable = false,name = "user_pw")
	private String userPw;
	
	@Column(nullable = false,name = "nickname")
	private	String nickName;
	
	@Column(nullable = false, unique = true,name = "account_id")
	private String accountId;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(nullable = false,name = "account_type")
	private UserAccount accountType;

	@CreatedDate
	@Column(updatable = false, nullable = false,name = "create_time")
	private LocalDateTime createTime;
	
	@Column(nullable = false)
	private boolean quit;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
	@JsonIgnore
	private List<Board> boards = new ArrayList<Board>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
	@JsonIgnore
	private List<Heart> hearts = new ArrayList<Heart>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
	@JsonIgnore
	private List<Comment> comments = new ArrayList<Comment>();
	
	
	public static User DTOToEntity(UserDTO userDTO) {
		User user = User.builder()
				.userNo(userDTO.getUserNo())
				.userPw(userDTO.getUserPw())
				.accountId(userDTO.getAccountId())
				.nickName(userDTO.getNickName())
				.accountType(userDTO.getAccountType())
				.createTime(userDTO.getCreateTime())
				.quit(userDTO.isQuit())
				.build();
		return user;
	}
	
	public void updateUser(String nickname, String password, UserAccount userAccount) {
		this.nickName = nickname;
		this.userPw = password;
		this.accountType =userAccount;
	}
	
	public void passwordEncoding(String password) {
		this.userPw = password;
	}
	
	public void withDrawUser(boolean quit) {
		this.quit = quit;
	}
	
}
