package com.spring.zaritalk.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class Heart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "heart_no")
	private Long heartNo;
	
	@Column(name = "do_heart")
	private boolean doHeart;
	
	
	// Join----------------------------------------
	
	@ManyToOne(fetch = FetchType.LAZY,optional = false)
	@JoinColumn(name = "user_no",nullable = false)
	@JsonIgnore
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY,optional = false)
	@JoinColumn(name = "board_no",nullable = false)
	@JsonIgnore
	private Board board;
	
	public void updateUser(User user) {
		this.user = user;
	}
	
	public void updateBoard(Board board) {
		this.board = board;
	}
	
	public void updateDoheart(Boolean doheart) {
		this.doHeart = doheart;
	}
	
	
	
}
