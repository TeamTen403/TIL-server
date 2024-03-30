package com.teamten.til.common.exception;

import com.teamten.til.common.dto.ResponseType;

public class InvalidException extends CustomException {
	public InvalidException() {
		super(ResponseType.INVALID, ResponseType.INVALID.getMessage());
	}

	public InvalidException(String message) {
		super(ResponseType.INVALID, message);
	}
}
