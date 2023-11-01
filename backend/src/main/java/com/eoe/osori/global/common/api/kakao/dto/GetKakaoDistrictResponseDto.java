package com.eoe.osori.global.common.api.kakao.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetKakaoDistrictResponseDto {
	private Document[] documents;

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Document {
		private Address address;

		@Getter
		@Builder
		@NoArgsConstructor
		@AllArgsConstructor
		public static class Address {
			private String region_1depth_name;
			private String region_2depth_name;
		}
	}
}
