package com.eoe.osori.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class PostAuthInfoResponseDto {

	private Long id;
	private String nickname;
	private String profileImageUrl;

	public static PostAuthInfoResponseDto of(Long id, String nickname, String profileImageUrl) {
		return PostAuthInfoResponseDto.builder()
			.id(id)
			.nickname(nickname)
			.profileImageUrl(profileImageUrl)
			.build();
	}
}
