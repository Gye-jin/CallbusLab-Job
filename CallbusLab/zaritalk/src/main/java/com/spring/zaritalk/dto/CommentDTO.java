package com.spring.zaritalk.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CommentDTO {
	private Long commentNo;
	private String Content;
	private LocalDateTime writtenDatetime;
	private LocalDateTime modifiedDatetime;
	private BoardDTO board;
	private UserDTO user;
	
}
