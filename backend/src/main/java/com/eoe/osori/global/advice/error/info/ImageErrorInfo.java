package com.eoe.osori.global.advice.error.info;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ImageErrorInfo {
	IMAGE_NOT_FOUND_ERROR(HttpStatus.NOT_FOUND, 1400, "등록할 사진 파일이 존재하지 않습니다."),
	IMAGE_FILE_IO_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,1401,"사진 파일 읽기에 실패했습니다." ),
	AMAZON_S3_SERVICE_UPLOAD_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,1402,"사진 파일 업로드에 실패했습니다. s3 서버 에러" ),
	AMAZON_S3_CLIENT_UPLOAD_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,1403,"사진 파일 업로드에 실패했습니다. s3 클라이언트 에러" ),
	IMAGE_URL_NOT_FOUND_ERROR(HttpStatus.NOT_FOUND,1404,"삭제할 사진 URL이 존재하지 않습니다."),
	AMAZON_S3_SERVICE_DELETE_ERROR(HttpStatus.NOT_FOUND,1405,"사진 삭제에 실패했습니다. s3 서버 에러"),
	AMAZON_S3_CLIENT_DELETE_ERROR(HttpStatus.NOT_FOUND,1406,"사진 삭제에 실패했습니다. s3 클라이언트 에러");

	private final HttpStatus status;
	private final Integer code;
	private final String message;

	ImageErrorInfo(HttpStatus status, Integer code, String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}
}
