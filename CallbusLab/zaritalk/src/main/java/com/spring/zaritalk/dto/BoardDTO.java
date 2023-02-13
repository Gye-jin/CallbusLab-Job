package com.spring.zaritalk.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.spring.zaritalk.model.Board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BoardDTO {
	private Long boardNo;
	private String boardTitle;
	private String boardContent;
	private LocalDateTime writtenDatetime;
	private LocalDateTime modifiedDatetime;
	private UserDTO user;
	private List<CommentDTO> comments = new ArrayList<>();
	private List<HeartDTO> hearts = new ArrayList<>();
	
	
	public static BoardDTO EntityToDTO(Board board) {
		BoardDTO boardDTO = BoardDTO.builder()
				.boardTitle(board.getBoardContent())
				.boardNo(board.getBoardNo())
				.boardContent(board.getBoardContent())
				.writtenDatetime(board.getWrittenDatetime())
				.modifiedDatetime(board.getModifiedDatetime())
				.user(UserDTO.EntityToDTO(board.getUser()))
				.comments(board.getComments().stream()
						.map(comment -> CommentDTO.EntityToDTO(comment))
						.collect(Collectors.toList()))
				.build();
		return boardDTO;
	}
}
