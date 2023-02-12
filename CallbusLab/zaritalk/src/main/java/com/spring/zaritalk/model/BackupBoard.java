package com.spring.zaritalk.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
//entity 수정사항을 계속 확인하게 하는 annotation, @createdDate, @LastModifiedDate annoatation사용을 위한 설정
@EntityListeners(AuditingEntityListener.class)	
@Builder
public class BackupBoard {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long boardNo;
	
	private String boardContent;
	
	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime writtenDatetime;
	
	@LastModifiedDate
	private LocalDateTime modifiedDatetime;
	
	


}
