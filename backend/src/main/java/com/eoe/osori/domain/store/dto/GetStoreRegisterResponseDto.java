package com.eoe.osori.domain.store.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class GetStoreRegisterResponseDto {
	private Boolean isRegistered;
	private Long storeId;

	public static GetStoreRegisterResponseDto of(Boolean isRegistered, Long storeId) {
		return GetStoreRegisterResponseDto.builder()
			.isRegistered(isRegistered)
			.storeId((isRegistered) ? storeId : null)
			.build();
	}
}
