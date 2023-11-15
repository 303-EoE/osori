package com.eoe.osori.global.common.redis.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@RedisHash("storeInfo")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StoreInfo {
	@Id
	private Long storeId;

	private Integer billTypeTotalPrice;

	private Integer billTypeTotalReviewCount;

	private Integer averagePrice;

	private Double totalRate;

	private Integer totalReviewCount;

	private Double averageRate;

	public void addBillTypeTotalPrice(Integer amount) {
		this.billTypeTotalPrice += amount;
	}

	public void plusBillTypeTotalReviewCount() {
		this.billTypeTotalReviewCount++;
	}

	public void calcAveragePrice(Integer billTypeTotalPrice, Integer billTypeTotalReviewCount) {
		if (billTypeTotalReviewCount.equals(0)) {
			this.averagePrice = 0;
			return;
		}

		this.averagePrice = billTypeTotalPrice / billTypeTotalReviewCount;
	}

	public void addTotalRate(Double amount) {
		this.totalRate += amount;
	}

	public void plusTotalReviewCount() {
		this.totalReviewCount++;
	}

	public void calcAverageRate(Double totalRate, Integer totalReviewCount) {
		if (totalReviewCount.equals(0)) {
			this.averageRate = 0.0;
			return;
		}

		this.averageRate = Math.round((totalRate / totalReviewCount) * 10) / 10.0;
	}

	public static StoreInfo from(Long storeId) {
		return StoreInfo.builder()
			.storeId(storeId)
			.billTypeTotalPrice(0)
			.billTypeTotalReviewCount(0)
			.averagePrice(0)
			.totalRate(0.0)
			.totalReviewCount(0)
			.averageRate(0.0)
			.build();
	}
}
