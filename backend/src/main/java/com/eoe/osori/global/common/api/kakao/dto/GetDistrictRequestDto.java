package com.eoe.osori.global.common.api.kakao.dto;

import com.eoe.osori.domain.store.dto.PostStoreRequestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class GetDistrictRequestDto {
	private String longitude;
	private String latitude;

	public static GetDistrictRequestDto from(PostStoreRequestDto postStoreRequestDto) {
		return GetDistrictRequestDto.builder()
			.longitude(postStoreRequestDto.getLongitude())
			.latitude(postStoreRequestDto.getLatitude())
			.build();
	}
}
