package com.eoe.osori.global.common.api.review.dto;

import com.eoe.osori.global.common.redis.domain.StoreInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class GetStoreReviewCacheDataResponseDto {

	private Long storeId;
	private Integer billTypeTotalPrice;
	private Integer billTypeTotalReviewCount;
	private Integer averagePrice;
	private Double totalRate;
	private Integer totalReviewCount;
	private Double averageRate;
}
