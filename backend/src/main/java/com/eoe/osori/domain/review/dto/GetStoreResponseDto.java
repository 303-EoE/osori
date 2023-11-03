package com.eoe.osori.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class GetStoreResponseDto {

	private Long id;
	private String name;
	private String depth1;
	private String depth2;
}
