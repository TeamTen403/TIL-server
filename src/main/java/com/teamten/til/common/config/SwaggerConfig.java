package com.teamten.til.common.config;

import java.time.YearMonth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.Operation;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
	private static final String API_NAME = "TIL Service API";
	private static final String API_VERSION = "1.0.0";
	private static final String API_DESCRIPTION = "틸(TIL) API 명세서";

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.OAS_30)
			// .ignoredParameterTypes(LoginUser.class)
			.useDefaultResponseMessages(false)
			.select()
			.apis(RequestHandlerSelectors.withMethodAnnotation(Operation.class)) // Operation이 설정되어 있는 것만 지정
			.paths(PathSelectors.any())
			.build()
			.directModelSubstitute(YearMonth.class, String.class)
			.apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
			.title(API_NAME)
			.description(API_DESCRIPTION)
			.version(API_VERSION)
			.build();
	}
}