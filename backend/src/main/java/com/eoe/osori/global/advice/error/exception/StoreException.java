package com.eoe.osori.global.advice.error.exception;

import com.eoe.osori.global.advice.error.info.StoreErrorInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StoreException extends RuntimeException {
	private final StoreErrorInfo info;
}