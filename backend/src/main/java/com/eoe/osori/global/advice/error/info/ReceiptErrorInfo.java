package com.eoe.osori.global.advice.error.info;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ReceiptErrorInfo {
    RECEIPT_FILE_IO_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 1250, "영수증 파일 읽기에 실패했습니다."),
    RECEIPT_FOLDER_CREATION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,1251,"폴더 생성에 실패했습니다."),
    RECEIPT_FILE_NOT_FOUND_ERROR(HttpStatus.NOT_FOUND,1252,"영수증 파일이 존재하지 않습니다."),
    INVALID_RECEIPT_FILE(HttpStatus.BAD_REQUEST, 1253, "영수증 사진 파일이 아닙니다."),
    RECEIPT_SERVICE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,1254,"영수증 파일 분석 중에 오류가 발생했습니다.");

    private final HttpStatus status;
    private final Integer code;
    private final String message;

    ReceiptErrorInfo(HttpStatus status, Integer code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
