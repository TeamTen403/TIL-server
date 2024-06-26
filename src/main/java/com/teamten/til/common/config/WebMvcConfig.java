package com.teamten.til.common.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	private final long MAX_AGE_SECS = 3600;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry
			// CORS 적용할 URL 패턴
			.addMapping("/**")
			// 자원을 공유할 오리진 지정
			.allowedOriginPatterns("*")
			// 요청 허용 메서드
			.allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
			// 요청 허용 헤더
			.allowedHeaders("*")
			// 쿠키 허용
			.allowCredentials(true)
			.maxAge(MAX_AGE_SECS);
	}

	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(new CustomAuthenticationPrincipalArgumentResolver());
	}
}