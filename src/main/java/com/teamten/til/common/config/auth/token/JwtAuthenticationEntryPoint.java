package com.teamten.til.common.config.auth.token;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teamten.til.common.dto.ResponseType;
import com.teamten.til.common.exception.UnauthorizedException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Autowired
	private ObjectMapper mapper;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException authException) throws IOException {

		response.setContentType("application/json;charset=UTF-8");
		response.setStatus(ResponseType.UNAUTHORIZED.getHttpStatus().value());
		response.getWriter().println(mapper.writeValueAsString(new UnauthorizedException()));
	}
	
}
