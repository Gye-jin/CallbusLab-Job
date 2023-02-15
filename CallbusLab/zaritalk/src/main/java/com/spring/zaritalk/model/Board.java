package com.spring.zaritalk.model;

import java.sql.Timestamp;
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
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.zaritalk.dto.BoardDTO;
import com.spring.zaritalk.repository.CommentRepository;

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
@Table(indexes = {
		@Index(name = "idx__boardNo",columnList = "board_title")
})
public class Board {
	
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "board_no")
	private Long boardNo;
	
	@Column(nullable = false, name = "board_title")
	private String boardTitle;
	
	@Column(nullable = false,length = 1000000,name = "board_content")
	private String boardContent;
	
	@CreatedDate
	@Column(updatable = false,nullable = false,name = "written_datetime")
	private LocalDateTime writtenDatetime;
	
	@LastModifiedDate
	@Column(nullable = false,name = "modified_datetime")
	private LocalDateTime modifiedDatetime;
	
	@Column(name = "deleted_datetime")
	private LocalDateTime deletedDatetime;
	
	@Column(nullable = false,name = "heart_cnt")
	private Long heartCnt;
	
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
				.heartCnt(boardDTO.getHeartCnt())
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
	
	public void plusHeartCnt() {
		this.heartCnt+=1;
	}
	
	public void minusHeartCnt() {
		this.heartCnt-=1;
	}
	
	public void setHeart() {
		this.heartCnt=0L;
	}
	
	public void deleteBoard() {
			this.deletedDatetime = LocalDateTime.now();
	}

}
