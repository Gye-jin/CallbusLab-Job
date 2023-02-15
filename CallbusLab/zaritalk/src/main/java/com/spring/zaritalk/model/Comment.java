package com.spring.zaritalk.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.zaritalk.dto.CommentDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EntityListeners(AuditingEntityListener.class)	
@Builder
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "comment_no")
	private Long commentNo;
	
	@Column(nullable = false,name = "comment_content")
	private String commentContent;
	
	@CreatedDate
	@Column(updatable = false,nullable = false, name = "written_datetime")
	private LocalDateTime writtenDatetime;
	
	@LastModifiedDate
	@Column(nullable = false,name = "modified_datetime")
	private LocalDateTime modifiedDatetime;
	
	@Column(name = "deleted_datetime")
	private LocalDateTime deletedDatetime;
	
	// Join
	// --------------------------------------------------------------------------------------------------------------------------------
	// [Board Join]
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "board_no",nullable = false)
	@JsonIgnore
	private Board board;
	
	// [User Join]
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_no",nullable = false)
	@JsonIgnore
	private User user;
	
	
	public static Comment DTOToEntity(CommentDTO commentDTO) {
		Comment comment = Comment.builder()
								 .commentNo(commentDTO.getCommentNo())
								 .commentContent(commentDTO.getCommentContent())
								 .writtenDatetime(commentDTO.getWrittenDatetime())
								 .modifiedDatetime(commentDTO.getModifiedDatetime())
								 .build();
		return comment;
	}
	
	public void updateUser(User user) {
		this.user = user;
	}
	
	public void updateBoard(Board board) {
		this.board = board;
	}
	
	public void updateContent(String Content) {
		this.commentContent = Content;
	}
	public void deleteComment() {
		this.deletedDatetime = LocalDateTime.now();
}

}
