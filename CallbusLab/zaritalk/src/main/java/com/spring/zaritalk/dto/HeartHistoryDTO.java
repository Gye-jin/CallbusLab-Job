package com.spring.zaritalk.dto;

import com.spring.zaritalk.model.Heart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class HeartHistoryDTO {
	
	private Long heartNo;
	private boolean doHeart;
	private Long boardNo;
	
	public static HeartHistoryDTO EntityToDTO(Heart heart) {
		HeartHistoryDTO heartHistryDTO = HeartHistoryDTO.builder()
				.heartNo(heart.getHeartNo())
				.doHeart(heart.isDoHeart())
				.boardNo(heart.getBoard().getBoardNo())
				.build();
		return heartHistryDTO;
	}
}
