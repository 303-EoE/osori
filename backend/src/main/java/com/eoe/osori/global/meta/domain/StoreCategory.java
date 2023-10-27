package com.eoe.osori.global.meta.domain;

import java.util.Arrays;

import com.eoe.osori.global.advice.error.exception.MetaException;
import com.eoe.osori.global.advice.error.info.MetaErrorInfo;

import lombok.Getter;

@Getter
public enum StoreCategory {
	RESTAURANT(1, "음식점", BillType.COUNT_BILL),
	FITNESS_CENTER(2, "헬스장", BillType.MONTHLY_BILL),
	NAIL_SHOP(3, "네일샵", BillType.COUNT_BILL);

	private final Integer id;
	private final String name;
	private BillType defaultBillType;

	StoreCategory(int id, String name, BillType defaultBillType) {
		this.id = id;
		this.name = name;
		this.defaultBillType = defaultBillType;
	}

	public static StoreCategory ofName(String name) {
		return Arrays.stream(StoreCategory.values())
			.filter(value -> value.getName().equals(name))
			.findAny()
			.orElseThrow(() -> new MetaException(MetaErrorInfo.INVALID_METADATA));
	}
}
