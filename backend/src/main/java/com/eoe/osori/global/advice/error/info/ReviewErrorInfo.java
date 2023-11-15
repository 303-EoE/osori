package com.eoe.osori.global.advice.error.info;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ReviewErrorInfo {
	INVALID_REVIEW_REQUEST_DATA_ERROR(HttpStatus.BAD_REQUEST, 1200, "유효하지 않은 요청 정보입니다."),
	DUPLICATE_RECEIPT_REQUEST_ERROR(HttpStatus.BAD_REQUEST, 1201, "이미 리뷰가 등록된 영수증입니다."),
	NOT_FOUND_REVIEW_BY_ID(HttpStatus.BAD_REQUEST, 1202, "해당 리뷰가 존재하지 않습니다."),
	NOT_MATCH_REVIEW_BY_MEMBERID(HttpStatus.BAD_REQUEST, 1203, "본인이 작성한 리뷰가 아닙니다."),
	INVALID_REVIEW_IMAGES_DATA_ERROR(HttpStatus.BAD_REQUEST, 1204, "리뷰 이미지가 등록되지 않았습니다."),
	FAIL_TO_STORE_FEIGN_CLIENT_REQUEST(HttpStatus.BAD_REQUEST, 1205, "Store Feign Client 통신에 실패했습니다."),
	NOT_FOUND_REVIEWFEED_BY_ID(HttpStatus.BAD_REQUEST, 1206, "해당 리뷰피드가 존재하지 않습니다."),
	FAIL_TO_IMAGE_FEIGN_CLIENT_REQUEST(HttpStatus.BAD_REQUEST, 1207, "Image Feign Client 통신에 실패했습니다.");

	private final HttpStatus status;
	private final Integer code;
	private final String message;

	ReviewErrorInfo(HttpStatus status, Integer code, String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}
}
