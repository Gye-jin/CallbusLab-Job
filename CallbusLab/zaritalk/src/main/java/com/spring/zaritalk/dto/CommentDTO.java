package com.spring.zaritalk.dto;

import java.time.LocalDateTime;

import com.spring.zaritalk.model.Comment;

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
	
	
	public static CommentDTO EntityToDTO(Comment comment) {
		CommentDTO commentDTO = CommentDTO.builder()
										  .commentNo(comment.getCommentNo())
										  .Content(comment.getContent())
										  .writtenDatetime(comment.getWrittenDatetime())
										  .modifiedDatetime(comment.getModifiedDatetime())
										  .build();
				return commentDTO;
	}
	
}
