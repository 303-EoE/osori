package com.eoe.osori.global.meta.converter;

import com.eoe.osori.global.meta.domain.BillType;

import jakarta.persistence.AttributeConverter;

public class BillTypeConverter implements AttributeConverter<BillType, String> {
	@Override
	public String convertToDatabaseColumn(BillType attribute) {
		return attribute.getName();
	}

	@Override
	public BillType convertToEntityAttribute(String dbData) {
		return BillType.ofName(dbData);
	}
}
