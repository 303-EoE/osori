package com.eoe.osori.global.advice.error.info;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ChatErrorInfo {
    FAIL_TO_MEMBER_FEIGN_CLIENT_REQUEST(HttpStatus.BAD_REQUEST, 1500, "Member Feign Client 통신에 실패했습니다.");

    private final HttpStatus status;
    private final Integer code;
    private final String message;

    ChatErrorInfo(HttpStatus status, Integer code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
