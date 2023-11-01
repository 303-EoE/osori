package com.eoe.osori.global.common.api.kakao.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class GetDistrictResponseDto {
	private String depth1;
	private String depth2;

	public static GetDistrictResponseDto from(GetKakaoDistrictResponseDto getKakaoDistrictResponseDto) {
		return GetDistrictResponseDto.builder()
			.depth1(getKakaoDistrictResponseDto.getDocuments()[0].getAddress().getRegion_1depth_name())
			.depth2(getKakaoDistrictResponseDto.getDocuments()[0].getAddress().getRegion_2depth_name())
			.build();
	}
}
