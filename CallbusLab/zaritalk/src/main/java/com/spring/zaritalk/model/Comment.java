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
	private Long commentNo;
	
	@Column(updatable = false,nullable = false)
	private String Content;
	
	@CreatedDate
	@Column(updatable = false,nullable = false)
	private LocalDateTime writtenDatetime;
	
	@Column(nullable = false)
	@LastModifiedDate
	private LocalDateTime modifiedDatetime;
	
	// Join
	// --------------------------------------------------------------------------------------------------------------------------------
	// [Board Join]
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "board_no",nullable = false)
	private Board board;
	
	// [User Join]
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id",nullable = false)
	private User user;

}
