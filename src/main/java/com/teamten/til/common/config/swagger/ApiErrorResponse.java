package com.teamten.til.common.config.swagger;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.teamten.til.common.dto.ResponseType;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiErrorResponse {
	Class<? extends ResponseType> value();

	int[] errorCodes() default {500}; // int 배열 형태로 에러 코드를 받도록 수정
}
