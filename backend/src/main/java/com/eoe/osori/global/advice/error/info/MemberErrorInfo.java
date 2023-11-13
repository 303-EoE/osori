package com.eoe.osori.global.advice.error.info;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum MemberErrorInfo {
	INVALID_MEMBER_REQUEST_DATA_ERROR(HttpStatus.BAD_REQUEST, 1100, "유효하지 않은 요청 정보입니다."),
	FAIL_TO_AUTH_FEIGN_CLIENT_REQUEST(HttpStatus.BAD_REQUEST, 1101, "Auth Feign Client 통신에 실패했습니다.");

	private final HttpStatus status;
	private final Integer code;
	private final String message;

	MemberErrorInfo(HttpStatus status, Integer code, String message){
		this.status = status;
		this.code = code;
		this.message = message;
	}
}
