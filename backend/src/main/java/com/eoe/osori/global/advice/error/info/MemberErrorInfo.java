package com.eoe.osori.global.advice.error.info;

import org.springframework.http.HttpStatus;

public enum MemberErrorInfo {

	INVALID_AUTH_REQUEST_DATA_ERROR(HttpStatus.BAD_REQUEST, 1000, "인자가 부족합니다."),
	MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, 1001, "사용자를 찾을 수 없습니다."),
	MISMATCH_TOKEN_ID(HttpStatus.CONFLICT, 1002, "토큰 생성에 사용된 아이디와 일치하지 않습니다."),
	INVALID_TOKEN(HttpStatus.UNAUTHORIZED, 1003, "유효하지 않은 토큰입니다.");

	private final HttpStatus status;
	private final Integer code;
	private final String message;

	MemberErrorInfo(HttpStatus status, Integer code, String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}
}
