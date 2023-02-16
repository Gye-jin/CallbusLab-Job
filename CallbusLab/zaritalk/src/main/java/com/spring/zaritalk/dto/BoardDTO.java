package com.spring.zaritalk.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.spring.zaritalk.model.Board;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BoardDTO {
	@ApiModelProperty(value = "게시글번호", required = true, hidden = false)
	private Long boardNo;
	
	@ApiModelProperty(value = "게시글 제목", required = true)
	private String boardTitle;
	
	@ApiModelProperty(value = "게시글 내용", required = true)
	private String boardContent;
	
	@ApiModelProperty(required = false,hidden = true)
	private LocalDateTime writtenDatetime;
	
	@ApiModelProperty(required = false,hidden = true)
	private LocalDateTime modifiedDatetime;
	
	@ApiModelProperty(required = false,hidden = true)
	private Long heartCnt;
	
	@ApiModelProperty(required = false,hidden = true)
	private UserDTO user;
	
	@ApiModelProperty(required = false,hidden = true)
	private List<CommentDTO> comments = new ArrayList<>();
	
	@ApiModelProperty(required = false,hidden = true)
	private List<HeartDTO> hearts = new ArrayList<>();
	
			
		public static BoardDTO EntityToDTO(Board board) {
			BoardDTO boardDTO = BoardDTO.builder()
					.boardTitle(board.getBoardContent())
					.boardNo(board.getBoardNo())
					.boardContent(board.getBoardContent())
					.writtenDatetime(board.getWrittenDatetime())
					.modifiedDatetime(board.getModifiedDatetime())
					.user(UserDTO.EntityToDTO(board.getUser()))
					.heartCnt(board.getHeartCnt())
					.comments(board.getComments().stream()
							.filter(comment -> comment.getDeletedDatetime() == null)
							.map(comment -> CommentDTO.EntityToDTO(comment))
							.collect(Collectors.toList()))
					.hearts(board.getHearts().stream()
							.filter(heart -> heart.isDoHeart() == true)
							.map(heart -> HeartDTO.EntityToDTO(heart))
							.collect(Collectors.toList()))
					.build();
			return boardDTO;
	}
}
