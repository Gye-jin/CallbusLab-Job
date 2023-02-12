package com.spring.zaritalk.dto;

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
public class HeartDTO {
	private Long heartNo;
	private boolean doHeart;
	private User user;
	private Heart heart;

}
