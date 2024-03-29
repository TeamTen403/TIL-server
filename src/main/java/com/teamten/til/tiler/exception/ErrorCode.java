package com.teamten.til.tiler.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    EMAIL_DUPLICATED(HttpStatus.CONFLICT, ""),
    EMAIL_NOTFOUND(HttpStatus.NOT_FOUND, ""),
    INVALiD_PASSWORd(HttpStatus.UNAUTHORIZED, "");


    private HttpStatus httpStatus;
    private String message;

}
