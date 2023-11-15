package com.eoe.osori.global.common.redis.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import com.eoe.osori.global.common.api.review.dto.GetStoreReviewCacheDataResponseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@RedisHash("storeInfo")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreInfo {
	@Id
	private Long storeId;

	private Integer billTypeTotalPrice;

	private Integer billTypeTotalReviewCount;

	private Integer averagePrice;

	private Double totalRate;

	private Integer totalReviewCount;

	private Double averageRate;

	public static StoreInfo from(GetStoreReviewCacheDataResponseDto getStoreReviewCacheDataResponseDto) {
		return StoreInfo.builder()
			.storeId(getStoreReviewCacheDataResponseDto.getStoreId())
			.billTypeTotalPrice(getStoreReviewCacheDataResponseDto.getBillTypeTotalPrice())
			.billTypeTotalReviewCount(getStoreReviewCacheDataResponseDto.getBillTypeTotalReviewCount())
			.averagePrice(getStoreReviewCacheDataResponseDto.getAveragePrice())
			.totalRate(getStoreReviewCacheDataResponseDto.getTotalRate())
			.totalReviewCount(getStoreReviewCacheDataResponseDto.getTotalReviewCount())
			.averageRate(getStoreReviewCacheDataResponseDto.getAverageRate())
			.build();
	}
}
