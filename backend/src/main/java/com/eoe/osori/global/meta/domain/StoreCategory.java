package com.eoe.osori.global.meta.domain;

import java.util.Arrays;

import com.eoe.osori.global.advice.error.exception.MetaException;
import com.eoe.osori.global.advice.error.info.MetaErrorInfo;

import lombok.Getter;

@Getter
public enum StoreCategory {
	RESTAURANT(1, "음식점", BillType.COUNT_BILL, "음식점"),
	FITNESS_CENTER(2, "헬스장", BillType.MONTHLY_BILL, "헬스클럽"),
	NAIL_SHOP(3, "네일샵", BillType.COUNT_BILL, "네일샵");

	private final Integer id;
	private final String name;
	private final BillType defaultBillType;
	private final String kakaoName;

	StoreCategory(int id, String name, BillType defaultBillType, String kakaoName) {
		this.id = id;
		this.name = name;
		this.defaultBillType = defaultBillType;
		this.kakaoName = kakaoName;
	}

	public static StoreCategory ofName(String name) {
		return Arrays.stream(StoreCategory.values())
			.filter(value -> value.getName().equals(name))
			.findAny()
			.orElseThrow(() -> new MetaException(MetaErrorInfo.INVALID_METADATA));
	}

	public static StoreCategory ofKakaoName(String kakaoName) {
		return Arrays.stream(StoreCategory.values())
			.filter(value -> kakaoName.contains(value.getKakaoName()))
			.findAny()
			.orElseThrow(() -> new MetaException(MetaErrorInfo.INVALID_METADATA));
	}
}
