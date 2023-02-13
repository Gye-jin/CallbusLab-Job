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
	private String commentContent;
	private LocalDateTime writtenDatetime;
	private LocalDateTime modifiedDatetime;
	private Long boardNo;
	private String userId;
	
	
	public static CommentDTO EntityToDTO(Comment comment) {
		CommentDTO commentDTO = CommentDTO.builder()
										  .commentNo(comment.getCommentNo())
										  .commentContent(comment.getCommentContent())
										  .writtenDatetime(comment.getWrittenDatetime())
										  .modifiedDatetime(comment.getModifiedDatetime())
										  .boardNo(comment.getBoard().getBoardNo())
										  .userId(comment.getUser().getAccountId())
										  .build();
				return commentDTO;
	}
	
}
