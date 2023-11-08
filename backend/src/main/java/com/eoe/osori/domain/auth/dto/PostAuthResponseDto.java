package com.eoe.osori.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class PostAuthResponseDto {

	private String nickname;
	private String accessToken;
	private String refreshToken;

	public static PostAuthResponseDto of(String nickname, String accessToken, String refreshToken) {
		return PostAuthResponseDto.builder()
			.nickname(nickname)
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build();
	}
}
