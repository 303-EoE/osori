package com.eoe.osori.domain.review.dto;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

import com.eoe.osori.global.advice.error.exception.ReviewException;
import com.eoe.osori.global.advice.error.info.ReviewErrorInfo;

import lombok.Getter;

@Getter
public class PostReviewRequestDto {

	private LocalDateTime paidAt;
	private Integer totalPrice;
	private Integer headcount;
	private Integer factor;
	private String content;
	private Double rate;
	private Long storeId;
	private String billType;

	/**
	 * null값 체크
	 */
	public boolean findEmptyValue() {
		try {
			for (Field f : getClass().getDeclaredFields()) {
				if (f.get(this) == null) {
					return true;
				}
			}
			return false;
		} catch (IllegalAccessException e) {
			throw new ReviewException(ReviewErrorInfo.INVALID_REVIEW_REQUEST_DATA_ERROR);
		}
	}
}
