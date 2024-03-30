package com.teamten.til.common.exception;

import com.teamten.til.common.dto.ResponseType;

public class DuplicatedException extends CustomException {
	public DuplicatedException() {
		super(ResponseType.DUPLICATED, ResponseType.DUPLICATED.getMessage());
	}

	public DuplicatedException(String message) {
		super(ResponseType.DUPLICATED, message);
	}
}
