package com.spring.zaritalk.config.jwt;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.spring.zaritalk.common.ErrorCode;
import com.spring.zaritalk.config.JwtProperties;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
    	System.out.println(authException+"exception");
    	String exception = (String) request.getAttribute(JwtProperties.HEADER_STRING);
        String errorCode;
        
        if(exception.equals(ErrorCode.FORBIDDEN.toString())) {
        	errorCode = ErrorCode.FORBIDDEN.getStatus().toString();
        	setResponse (response, errorCode);
        }

        if(exception.equals(ErrorCode.UNAUTHORIZED.toString())) {
            errorCode = ErrorCode.UNAUTHORIZED.getStatus().toString();
            setResponse (response, errorCode);
        }

        if(exception.equals(ErrorCode.BAD_REQUEST.toString())) {
            errorCode = ErrorCode.BAD_REQUEST.getStatus().toString();
            setResponse(response, errorCode);
        }
    }

    private void setResponse(HttpServletResponse response, String errorCode) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        log.error("토큰에러");
        response.getWriter().println(JwtProperties.HEADER_STRING + " : " + errorCode);

 
    }
}


