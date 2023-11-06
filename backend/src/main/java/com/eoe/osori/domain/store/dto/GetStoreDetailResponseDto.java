package com.eoe.osori.domain.store.dto;

import com.eoe.osori.domain.store.domain.Store;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class GetStoreDetailResponseDto {
	private Long id;
	private String name;
	private String category;
	private String roadAddressName;
	private String addressName;
	private String phone;
	private Double avergeRate;
	private Integer avergePrice;
	private Integer totalReviewCount;
	private String defaultBillType;
	private String depth1;
	private String depth2;

	public static GetStoreDetailResponseDto from(Store store) {
		return GetStoreDetailResponseDto.builder()
			.id(store.getId())
			.name(store.getName())
			.category(store.getCategory().getName())
			.roadAddressName(store.getRoadAddressName())
			.addressName(store.getAddressName())
			.phone(store.getPhone())
			.avergeRate(3.5)
			.avergePrice(10000)
			.totalReviewCount(10)
			.defaultBillType(store.getCategory().getDefaultBillType().getName())
			.depth1(store.getDepth1())
			.depth2(store.getDepth2())
			.build();
	}
}
