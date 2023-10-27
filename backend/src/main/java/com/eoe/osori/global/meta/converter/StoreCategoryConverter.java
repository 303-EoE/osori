package com.eoe.osori.global.meta.converter;

import com.eoe.osori.global.meta.domain.StoreCategory;

import jakarta.persistence.AttributeConverter;

public class StoreCategoryConverter implements AttributeConverter<StoreCategory, String> {
	@Override
	public String convertToDatabaseColumn(StoreCategory attribute) {
		return attribute.getName();
	}

	@Override
	public StoreCategory convertToEntityAttribute(String dbData) {
		return StoreCategory.ofName(dbData);
	}
}
