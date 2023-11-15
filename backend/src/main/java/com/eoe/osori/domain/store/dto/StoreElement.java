package com.eoe.osori.domain.store.dto;

import com.eoe.osori.domain.store.domain.Store;
import com.eoe.osori.global.common.api.review.dto.GetStoreReviewCacheDataResponseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class StoreElement {
	private Long id;
	private String name;
	private String category;
	private String longitude;
	private String latitude;
	private String depth1;
	private String depth2;
	private Double averageRate;
	private Integer averagePrice;
	private String defaultBillType;

	public static StoreElement of(Store store, GetStoreReviewCacheDataResponseDto getStoreReviewCacheDataResponseDto) {
		return StoreElement.builder()
			.id(store.getId())
			.name(store.getName())
			.category(store.getCategory().getName())
			.longitude(store.getLongitude())
			.latitude(store.getLatitude())
			.depth1(store.getDepth1())
			.depth2(store.getDepth2())
			.averageRate(getStoreReviewCacheDataResponseDto.getAverageRate())
			.averagePrice(getStoreReviewCacheDataResponseDto.getAveragePrice())
			.defaultBillType(store.getCategory().getDefaultBillType().getName())
			.build();
	}
}
