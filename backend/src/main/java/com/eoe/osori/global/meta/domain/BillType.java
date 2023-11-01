package com.eoe.osori.global.meta.domain;

import java.util.Arrays;

import com.eoe.osori.global.advice.error.exception.MetaException;
import com.eoe.osori.global.advice.error.info.MetaErrorInfo;

import lombok.Getter;

@Getter
public enum BillType {
	COUNT_BILL(1, "횟수권"),
	TIME_BILL(2, "시간권"),
	DAILY_BILL(3, "일일권"),
	MONTHLY_BILL(4, "개월권"),
	PRICE_BILL(5, "금액권"),
	EXTRA_BILL(6, "기타");

	private final Integer id;
	private final String name;

	BillType(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public static BillType ofName(String name) {
		return Arrays.stream(BillType.values())
			.filter(value -> value.getName().equals(name))
			.findAny()
			.orElseThrow(() -> new MetaException(MetaErrorInfo.INVALID_METADATA));
	}
}
