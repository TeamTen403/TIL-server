package com.teamten.til.common.dto;

import com.teamten.til.common.exception.CustomException;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ExceptionResponse {
	private String code;
	private String message;

	public static ExceptionResponse from(CustomException exception) {
		return ExceptionResponse.builder().code(exception.getCode().getCode()).message(exception.getMessage()).build();
	}
}
