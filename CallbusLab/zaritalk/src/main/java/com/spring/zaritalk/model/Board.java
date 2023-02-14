package com.spring.zaritalk.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.zaritalk.dto.BoardDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {"comments", "hearts"})
@EntityListeners(AuditingEntityListener.class)	
@Builder
public class Board {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long boardNo;
	
	@Column(nullable = false)
	private String boardTitle;
	
	@Column(nullable = false,length = 1000000)
	private String boardContent;
	
	@CreatedDate
	@Column(updatable = false,nullable = false)
	private LocalDateTime writtenDatetime;
	
	@LastModifiedDate
	@Column(nullable = false)
	private LocalDateTime modifiedDatetime;
	
	
	
	// [User Join]
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_no",nullable = false)
	private User user;
	
	// [Heart Join]
	@OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JsonIgnore
	private List<Heart> hearts = new ArrayList<Heart>();
	
//	// [Comment Join]
	@OneToMany(mappedBy = "board",cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Comment> comments = new ArrayList<Comment>();
	
	
	public static Board DTOToEntity(BoardDTO boardDTO) {
		Board board = Board.builder()
				.boardNo(boardDTO.getBoardNo())
				.boardTitle(boardDTO.getBoardTitle())
				.boardContent(boardDTO.getBoardContent())
				.writtenDatetime(boardDTO.getWrittenDatetime())
				.modifiedDatetime(boardDTO.getModifiedDatetime())
				.build();
		
		return board;
	}
	
	
	public void updateUser(User user) {
		this.user = user;
	}
	public void updateTitle(String boardtitle) {
		this.boardTitle = boardtitle;
	}
	public void updateContent(String boardcontent) {
		this.boardContent = boardcontent;
	}
	
	

}