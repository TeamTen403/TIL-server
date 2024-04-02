package com.teamten.til.common.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class ResponseDto<T> implements Serializable {
	@Schema(description = "응답코드", example = "SUCCESS")
	private String code;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Schema(description = "데이터", nullable = true)
	private T data;

	public static <T> ResponseDto<T> code(ResponseType responseType) {
		return ResponseDto.<T>builder().code(responseType.getCode()).build();
	}

	public static <T> ResponseDto<T> code(ResponseType responseType, T data) {
		return ResponseDto.<T>builder().code(responseType.getCode()).data(data).build();
	}

	public static <T> ResponseDto<T> ok() {
		return ResponseDto.code(ResponseType.SUCCESS);
	}

	public static <T> ResponseDto<T> ok(T data) {
		return ResponseDto.code(ResponseType.SUCCESS, data);
	}
}
