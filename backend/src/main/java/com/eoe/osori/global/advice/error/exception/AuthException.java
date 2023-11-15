package com.eoe.osori.global.advice.error.exception;

import com.eoe.osori.global.advice.error.info.AuthErrorInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@AllArgsConstructor
public class AuthException extends RuntimeException {
	private final AuthErrorInfo info;
}
