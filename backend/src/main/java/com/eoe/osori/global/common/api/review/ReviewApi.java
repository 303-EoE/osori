package com.eoe.osori.global.common.api.review;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.eoe.osori.global.common.api.review.dto.GetStoreReviewCacheDataResponseDto;
import com.eoe.osori.global.common.response.EnvelopeResponse;

@FeignClient(name = "review", url = "${api.review}")
public interface ReviewApi {
	@GetMapping("/cache-data")
	EnvelopeResponse<GetStoreReviewCacheDataResponseDto> getReviewCacheDataByStore(
		@RequestParam("store_id") Long storeId, @RequestParam("bill_type") String defaultBillType);
}
