package com.teamten.til.common.config;

import java.time.YearMonth;

import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
	private static final String API_NAME = "TIL Service API";
	private static final String API_DESCRIPTION = "틸(TIL) API 명세서";
	private static final String API_VERSION = "1.0.0";

	static {
		SpringDocUtils.getConfig().replaceWithClass(YearMonth.class, String.class);
	}

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
			.info(new Info()
				.title(API_NAME)
				.description(API_DESCRIPTION)
				.version(API_VERSION));
	}
}