package com.teamten.til.common.dto;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseType {
	SUCCESS("SUCCESS", "성공", HttpStatus.OK),
	ERROR("ERROR", "서버에 오류가 발생하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
	UNAUTHORIZED("UNAUTHORIZED", "로그인이 필요합니다.", HttpStatus.UNAUTHORIZED),
	DUPLICATED("DUPLICATED", "이미 생성했습니다.", HttpStatus.CREATED),
	INVALID("INVALID", "유효하지 않은 요청입니다.", HttpStatus.BAD_REQUEST),
	NOT_EXIST("NOT_EXIST", "데이터가 존재하지 않습니다.", HttpStatus.NOT_FOUND);

	private final String code;
	private final String message;
	private final HttpStatus httpStatus;
}
