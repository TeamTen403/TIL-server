package com.teamten.til.common.config.swagger;

import static java.util.stream.Collectors.*;

import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springdoc.core.customizers.OperationCustomizer;
import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;

import com.teamten.til.common.dto.ExceptionResponse;
import com.teamten.til.common.dto.ResponseType;
import com.teamten.til.common.exception.CustomException;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.servers.Server;

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
			.addServersItem(new Server().url("/"))
			.info(new Info()
				.title(API_NAME)
				.description(API_DESCRIPTION)
				.version(API_VERSION));
	}

	@Bean
	public OperationCustomizer customize() {
		return (Operation operation, HandlerMethod handlerMethod) -> {
			ApiErrorResponse apiErrorResponse =
				handlerMethod.getMethodAnnotation(ApiErrorResponse.class);
			// ApiErrorCodeExample 어노테이션 단 메소드 적용
			if (apiErrorResponse != null) {
				generateErrorCodeResponseExample(operation, apiErrorResponse.value(),
					apiErrorResponse.errorCodes());
			}
			return operation;
		};
	}

	private void generateErrorCodeResponseExample(
		Operation operation, Class<? extends ResponseType> type, int[] errorCodes) {
		ApiResponses responses = operation.getResponses();
		// 해당 이넘에 선언된 에러코드들의 목록을 가져옵니다.
		ResponseType[] responseTypes = type.getEnumConstants();

		// 400, 401, 404 등 에러코드의 상태코드들로 리스트로 모읍니다.
		// 400 같은 상태코드에 여러 에러코드들이 있을 수 있습니다.
		Map<Integer, List<ExampleHolder>> statusWithExampleHolders =
			Arrays.stream(responseTypes)
				.filter(responseType -> Arrays.stream(errorCodes)
					.anyMatch(code -> code == responseType.getHttpStatus().value()))
				.map(
					responseType -> ExampleHolder.builder()
						.holder(
							getSwaggerExample(responseType))
						.code(responseType.getHttpStatus().value())
						.name(responseType.getCode())
						.build())
				.collect(groupingBy(ExampleHolder::getCode));

		addExamplesToResponses(responses, statusWithExampleHolders);
	}

	//
	private Example getSwaggerExample(ResponseType responseType) {
		//ErrorResponse 는 클라이언트한 실제 응답하는 공통 에러 응답 객체입니다.
		ExceptionResponse errorResponse = ExceptionResponse.from(new CustomException(responseType));
		Example example = new Example();
		example.description(responseType.getMessage());
		example.setValue(errorResponse);
		return example;
	}

	private void addExamplesToResponses(
		ApiResponses responses, Map<Integer, List<ExampleHolder>> statusWithExampleHolders) {
		statusWithExampleHolders.forEach(
			(status, v) -> {
				Content content = new Content();
				MediaType mediaType = new MediaType();
				// 상태 코드마다 ApiResponse을 생성합니다.
				ApiResponse apiResponse = new ApiResponse();
				//  List<ExampleHolder> 를 순회하며, mediaType 객체에 예시값을 추가합니다.
				v.forEach(
					exampleHolder -> mediaType.addExamples(
						exampleHolder.getName(), exampleHolder.getHolder()));
				// ApiResponse 의 content 에 mediaType을 추가합니다.
				content.addMediaType("application/json", mediaType);
				apiResponse.setContent(content);
				// 상태코드를 key 값으로 responses 에 추가합니다.
				responses.addApiResponse(status.toString(), apiResponse);
			});
	}
}