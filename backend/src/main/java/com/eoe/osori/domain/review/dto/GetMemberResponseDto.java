package com.eoe.osori.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class GetMemberResponseDto {

	private Long id;
	private String nickname;
	private String profileImageUrl;
}
