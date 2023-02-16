package com.spring.zaritalk.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.zaritalk.dto.HeartDTO;
import com.spring.zaritalk.dto.HeartHistoryDTO;
import com.spring.zaritalk.service.HeartServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api/board",produces = "application/json")
@Api(tags = "5. 좋아요 컨트롤러", description = "좋아요 하기, 유저가 좋아요한 내역 확인")
public class HeartController {
	
	@Autowired
	HeartServiceImpl heartService;
	
	// 좋아요 기능
	@PutMapping("/{boardNo}/heart")
	@ApiOperation(value = "좋아요 누르기 / 취소")
	public ResponseEntity<?> doheart(@PathVariable("boardNo") Long boardNo, HttpServletRequest request,
			@RequestBody HeartDTO heartDTO) {

		return heartService.doheart(boardNo, request.getSession(), heartDTO.isDoHeart());
	}
	
	
	
	// user가 좋아요한 게시글 가져오기
	@GetMapping("/heart/{accountId}")
	@ApiOperation(value = "user가 누른 좋아요 정보 가져오기")
	public ResponseEntity<?> getHeart(@PathVariable("accountId") String accountId,HttpServletRequest request) {	

		return new ResponseEntity<List<HeartHistoryDTO>>(heartService.getDoHeart(accountId,request.getSession()),HttpStatus.OK);
	}
	
	
	
}
