package com.teamten.til.common.exception;

import com.teamten.til.common.dto.ResponseType;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
	private ResponseType code;

	public CustomException() {
		super(ResponseType.ERROR.getMessage());
		this.code = ResponseType.ERROR;
	}

	public CustomException(ResponseType code, String message) {
		super(message);
		this.code = code;
	}

	public CustomException(ResponseType code) {
		super(code.getMessage());
		this.code = code;
	}

	public CustomException(String message) {
		super(message);
		code = ResponseType.ERROR;
	}

}
