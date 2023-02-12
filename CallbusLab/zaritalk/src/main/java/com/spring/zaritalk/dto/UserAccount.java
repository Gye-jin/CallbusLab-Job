package com.spring.zaritalk.dto;

import lombok.Getter;

@Getter
public enum UserAccount {
	LESSOR("임대인"),REALTOR("공인중개사"),LESSEE("임차인");
	
	private String userAccount;

	UserAccount(String userAccount){
		this.userAccount = userAccount;
	}
}

