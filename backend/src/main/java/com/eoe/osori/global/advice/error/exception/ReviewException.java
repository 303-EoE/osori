package com.eoe.osori.global.advice.error.exception;

import com.eoe.osori.global.advice.error.info.ReviewErrorInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewException extends RuntimeException{
	private final ReviewErrorInfo info;
}
