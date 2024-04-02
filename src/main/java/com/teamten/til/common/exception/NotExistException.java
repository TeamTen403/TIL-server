package com.teamten.til.common.exception;

import com.teamten.til.common.dto.ResponseType;

public class NotExistException extends CustomException {
	public NotExistException() {
		super(ResponseType.NOT_EXIST, ResponseType.NOT_EXIST.getMessage());
	}

	public NotExistException(String message) {
		super(ResponseType.NOT_EXIST, message);
	}
}
