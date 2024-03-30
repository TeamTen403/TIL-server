package com.teamten.til.common.exception;

import com.teamten.til.common.dto.ResponseType;

public class UnauthorizedException extends CustomException {
	public UnauthorizedException() {
		super(ResponseType.INVALID, ResponseType.INVALID.getMessage());
	}

	public UnauthorizedException(String message) {
		super(ResponseType.INVALID, message);
	}
}
