package com.teamten.til.common.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseType {
	SUCCESS("SUCCESS", "성공"),
	ERROR("ERROR", "서버에 오류가 발생하였습니다."),
	NOT_LOGIN("NOT_LOGIN", "로그인이 필요한 이벤트입니다."),
	ALREADY("ALREADY", "이미 참여하였습니다."),
	INVALID("INVALID", "유효하지 않은 요청입니다.");

	private final String code;
	private final String message;
}
