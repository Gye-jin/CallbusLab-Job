package com.spring.zaritalk.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.spring.zaritalk.model.Board;
import com.spring.zaritalk.model.User;

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
	private User user;
	private List<CommentDTO> comments = new ArrayList<>();
	private List<HeartDTO> hearts = new ArrayList<>();
	
	
	public static BoardDTO EntityToDTO(Board board) {
		BoardDTO boardDTO = BoardDTO.builder()
				.boardTitle(board.getBoardContent())
				.boardNo(board.getBoardNo())
				.boardContent(board.getBoardContent())
				.writtenDatetime(board.getWrittenDatetime())
				.modifiedDatetime(board.getModifiedDatetime())
				.build();
		return boardDTO;
	}
}
