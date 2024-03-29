package com.spring.zaritalk.config.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.spring.zaritalk.common.ErrorCode;
import com.spring.zaritalk.config.JwtProperties;
import com.spring.zaritalk.config.auth.LoginUser;
import com.spring.zaritalk.model.User;
import com.spring.zaritalk.repository.UserRepository;



// 시큐리티가 filter를 가지고 있는데 그 필터중에 BasicAuthenticationFilter라는 것이 있음.
// 권한이나 인증이 필요한 특정 주소를 요청했을 때, 위 필터를 무조건 타게 되어 있음.
// 만약 권한이나 인증이 필요한 주소가 아니라면 이 필터를 안탐.
public class JwtAuthrizationFilter extends BasicAuthenticationFilter{
	
	@Autowired
	UserRepository userRepository;

	public JwtAuthrizationFilter(AuthenticationManager authenticationManager, UserRepository userrepository) {
		super(authenticationManager);
		this.userRepository = userrepository;
		
	}
	// 인증이나 권하이 필요한 주소요청이 있을 때 해당 필터를 타게 됨.
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		System.out.println("인증이나 권한이 필요한 주소 요청이 됨.");
		String jwtHeader = request.getHeader(JwtProperties.HEADER_STRING);

		// 헤더가 있는지 확인
		if (jwtHeader == null || !jwtHeader.startsWith("Bearer")) {
			request.setAttribute(JwtProperties.HEADER_STRING, ErrorCode.UNAUTHORIZED.toString());

		} else {

			try {
				// jwt 토큰을 점증을 해서 정상적인 사용자인지 확인
				String jwttoken = request.getHeader(JwtProperties.HEADER_STRING).replace(JwtProperties.TOKEN_PREFIX,"");
				String accountId = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(jwttoken)
						.getClaim("AccountId").asString();
				
				String Authentication = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(jwttoken)
						.getClaim("Authentication").asString();
				
				User user = userRepository.findByAccountIdAndQuitIsFalse(accountId).orElseGet(User::new);
				LoginUser loginUser = new LoginUser(user);
				HttpSession session = request.getSession();
				session.setAttribute("loginUser", user);
				session.setAttribute("Authentication", Authentication);
				
				Authentication authentication = new UsernamePasswordAuthenticationToken(loginUser, null,loginUser.getAuthorities());

				SecurityContextHolder.getContext().setAuthentication(authentication);

			} catch (TokenExpiredException e) {
				request.setAttribute(JwtProperties.HEADER_STRING, ErrorCode.FORBIDDEN.toString());
			} catch (JWTVerificationException e) {
				request.setAttribute(JwtProperties.HEADER_STRING, ErrorCode.BAD_REQUEST.toString());
			}

		}
		chain.doFilter(request, response);
	}
	

	
	
}
