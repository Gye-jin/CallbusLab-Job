package com.spring.zaritalk.dto;

import java.time.LocalDateTime;

import com.spring.zaritalk.model.Comment;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CommentDTO {
	@ApiModelProperty(required = true,hidden = false,example = "0")
	private Long commentNo;
	@ApiModelProperty(value = "댓글 내용",required = true,hidden = false)
	private String commentContent;
	@ApiModelProperty(required = false,hidden = true)
	private LocalDateTime writtenDatetime;
	@ApiModelProperty(required = false,hidden = true)
	private LocalDateTime modifiedDatetime;
	@ApiModelProperty(required = false,hidden = true)
	private Long boardNo;
	@ApiModelProperty(required = false,hidden = true)
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
