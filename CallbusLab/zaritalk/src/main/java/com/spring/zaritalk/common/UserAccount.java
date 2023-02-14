package com.spring.zaritalk.common;

import lombok.Getter;

@Getter
public enum UserAccount {
	임대인("LESSOR"),공인중개사("REALTOR"),임차인("LESSEE");
	
	private String userAccount;

	UserAccount(String userAccount){
		this.userAccount = userAccount;
	}
}

