package com.spring.zaritalk.config.jwt;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.zaritalk.config.JwtProperties;
import com.spring.zaritalk.config.auth.LoginUser;
import com.spring.zaritalk.model.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// 스프링 시큐리티에서 UsernamePasswordAuthenticationFilter가 있음.
// login 요청해서 username, password 전송하면(post)
// usernamepasswordAuthenticationFilter 동작을 함.
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private final AuthenticationManager authenticationManager;

	// /login 요청을 하면 로그인 시도를 위해서 실행되는 함수
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		System.out.println("로그인 시도");

		// 1. username,password 받아서
		try {

			ObjectMapper om = new ObjectMapper();
			User user;
			user = om.readValue(request.getInputStream(), User.class);
			
			log.info("{}가 로그인 시도함.",user.getAccountId());		
			UsernamePasswordAuthenticationToken tuthenticationToken = new UsernamePasswordAuthenticationToken(
					user.getAccountId(), user.getUserPw());
			Authentication authentication = authenticationManager.authenticate(tuthenticationToken);
			// authentication 객체가 session 영역에 저장됨 => 로그인이 되었다는 뜻임.
			LoginUser loginUser = (LoginUser) authentication.getPrincipal();
			log.info("{}가 로그인 완료함.",loginUser.getUsername());
			return authentication;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};

		return null;
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		System.out.println("successfulAuthentication");
		LoginUser loginUser = (LoginUser) authResult.getPrincipal();
		String jwtToken = JWT.create()
				.withSubject(loginUser.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.EXPIRATION_TIME))
				.withClaim("AccountId",loginUser.getUser().getAccountId())
				.withClaim("Authentication", loginUser.getUser().getAccountType().toString())
				.withClaim("no", loginUser.getUser().getUserNo())
				.sign(Algorithm.HMAC512(JwtProperties.SECRET));
		System.out.println(jwtToken);
		response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX+jwtToken);
	}
	



}
