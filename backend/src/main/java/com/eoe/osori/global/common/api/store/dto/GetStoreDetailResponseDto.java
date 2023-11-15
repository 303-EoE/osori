package com.eoe.osori.global.common.api.store.dto;

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
	private String depth1;
	private String depth2;
	private String defaultBillType;
}
