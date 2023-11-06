package com.eoe.osori.global.common.api.store;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.eoe.osori.global.common.api.store.dto.GetStoreDetailResponseDto;
import com.eoe.osori.global.common.response.EnvelopeResponse;

@FeignClient(name = "store", url = "${api.store}")
public interface StoreApi {
	@GetMapping("/detail")
	EnvelopeResponse<GetStoreDetailResponseDto> getStoreDetail(@RequestParam("store_id") Long storeId);
}
