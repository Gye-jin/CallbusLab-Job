package com.spring.zaritalk.dto;

import com.spring.zaritalk.model.Heart;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class HeartDTO {
	@ApiModelProperty(required = false,hidden = true)
	private Long heartNo;
	@ApiModelProperty(required = true,hidden = false)
	private boolean doHeart;
	@ApiModelProperty(required = false,hidden = true)
	private String userId;
	
	
	
	public static HeartDTO EntityToDTO(Heart heart) {
		HeartDTO heartDTO = HeartDTO.builder()
				.heartNo(heart.getHeartNo())
				.doHeart(heart.isDoHeart())
				.userId(heart.getUser().getAccountId())
				.build();
		return heartDTO;
	}

}
