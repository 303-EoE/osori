package com.eoe.osori.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class PostAuthProfileResponseDto {

	private String nickname;
	private String accessToken;
	private String refreshToken;

	public static PostAuthProfileResponseDto of(String nickname, String accessToken, String refreshToken) {
		return PostAuthProfileResponseDto.builder()
			.nickname(nickname)
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build();
	}
}
