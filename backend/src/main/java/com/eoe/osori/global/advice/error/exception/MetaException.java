package com.eoe.osori.global.advice.error.exception;

import com.eoe.osori.global.advice.error.info.MetaErrorInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MetaException extends RuntimeException {
	private final MetaErrorInfo info;
}