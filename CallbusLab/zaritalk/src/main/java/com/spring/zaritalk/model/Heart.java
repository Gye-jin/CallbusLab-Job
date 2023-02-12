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
//entity 수정사항을 계속 확인하게 하는 annotation, @createdDate, @LastModifiedDate annoatation사용을 위한 설정
@EntityListeners(AuditingEntityListener.class)	
@Builder
public class Heart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long heartNo;
	
	private boolean doHeart;
	
	
	// Join----------------------------------------
	
	@ManyToOne(fetch = FetchType.LAZY )
	@JoinColumn(name = "user_id",nullable = false)
	@JsonIgnore
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "board_no",nullable = false)
	@JsonIgnore
	private Board board;
	
	
	public void updateLike(Boolean doheart) {
		this.doHeart = doheart;
	}
	
}
