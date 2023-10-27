package com.eoe.osori.global.advice.error.info;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum StoreErrorInfo {
	INVALID_STORE_REQUEST_DATA_ERROR(HttpStatus.BAD_REQUEST, 1300, "유효하지 않은 요청 정보입니다.");

	private final HttpStatus status;
	private final Integer code;
	private final String message;

	StoreErrorInfo(HttpStatus status, Integer code, String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}
}
