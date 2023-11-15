package com.eoe.osori.global.advice.error.info;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum AuthErrorInfo {

	INVALID_AUTH_REQUEST_DATA_ERROR(HttpStatus.BAD_REQUEST, 1000, "인자가 부족합니다."),
	MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, 1001, "사용자를 찾을 수 없습니다."),
	MISMATCH_TOKEN_ID(HttpStatus.CONFLICT, 1002, "토큰 생성에 사용된 아이디와 일치하지 않습니다."),
	INVALID_TOKEN(HttpStatus.UNAUTHORIZED, 1003, "유효하지 않은 토큰입니다."),
	EXIST_MEMBER_NICKNAME(HttpStatus.BAD_REQUEST, 1004, "이미 존재하는 닉네임입니다."),
	FAIL_TO_IMAGE_FEIGN_CLIENT_REQUEST(HttpStatus.BAD_REQUEST, 1005, "Image Feign Client 통신에 실패했습니다."),
	EXPIRED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, 1006, "Refresh 토큰이 만료되었습니다."),
	TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, 1007, "Redis에 토큰이 존재하지 않습니다."),
	EXPIRED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, 1008, "Access 토큰이 만료되었습니다."),
	IOEXCEPTION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 1009, "IOException 발생"),
	SERVLET_EXCEPTION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 1010, "ServletException 발생");

	private final HttpStatus status;
	private final Integer code;
	private final String message;

	AuthErrorInfo(HttpStatus status, Integer code, String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}
}
