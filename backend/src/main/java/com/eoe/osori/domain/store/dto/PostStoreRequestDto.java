package com.eoe.osori.domain.store.dto;

import java.lang.reflect.Field;

import com.eoe.osori.global.advice.error.exception.StoreException;
import com.eoe.osori.global.advice.error.info.StoreErrorInfo;

import lombok.Getter;

@Getter
public class PostStoreRequestDto {
	private String name;
	private String kakaoId;
	private String category;
	private String longitude;
	private String latitude;
	private String roadAddressName;
	private String addressName;
	private String phone;

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
			throw new StoreException(StoreErrorInfo.INVALID_STORE_REQUEST_DATA_ERROR);
		}
	}
}
