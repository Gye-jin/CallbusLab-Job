package com.spring.zaritalk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.filter.CorsFilter;

import com.spring.zaritalk.config.jwt.JwtAuthenticationFilter;
import com.spring.zaritalk.config.jwt.JwtAuthrizationFilter;
import com.spring.zaritalk.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	private final CorsFilter corsfilter;
	private final UserRepository userrepository;
	
	@Bean
	public BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests()
		.antMatchers("/login","/join","/board/**").permitAll()
		.antMatchers("/api/**").authenticated();
		
		http.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.addFilter(new JwtAuthenticationFilter(authenticationManager()))	//authenticationManager
		.addFilter(new JwtAuthrizationFilter(authenticationManager(),userrepository))	//authenticationManager
		.addFilter(corsfilter)// 기존의 crossorigin(인증 x), 시큐리티 필터에 등록 인증(o)
		.formLogin().disable()
		.httpBasic().disable();
		
	}
	
	
}
