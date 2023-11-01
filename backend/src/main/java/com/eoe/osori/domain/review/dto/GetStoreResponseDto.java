package com.eoe.osori.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class GetStoreResponseDto {

	private Long storeId;
	private String storeName;
	private String storeDepth1;
	private String storeDepth2;
}
