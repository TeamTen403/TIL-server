package com.teamten.til.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.teamten.til.common.dto.ExceptionResponse;
import com.teamten.til.common.dto.ResponseType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionResponse> handleException(Exception e) {
		log.error(toLogMessage(e));

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ExceptionResponse> handleCustomException(CustomException e) {
		log.error(toLogMessage(e));

		ResponseType responseType = e.getCode();

		return ResponseEntity.status(responseType.getHttpStatus()).body(ExceptionResponse.from(e));
	}

	private String toLogMessage(Exception e) {
		String exceptionName = e.getClass().getName();
		String message = e.getMessage();

		String className = e.getStackTrace()[0].getClassName();
		int lineNumber = e.getStackTrace()[0].getLineNumber();

		return String.format("%s: %s - at %s: line %d", exceptionName, message, className, lineNumber);
	}
}
