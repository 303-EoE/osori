package com.eoe.osori.global.advice.error.exception;

import com.eoe.osori.global.advice.error.info.MemberErrorInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberException extends RuntimeException {
	private final MemberErrorInfo info;
}
