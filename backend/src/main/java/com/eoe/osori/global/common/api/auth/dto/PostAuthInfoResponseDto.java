package com.eoe.osori.global.common.api.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class PostAuthInfoResponseDto {
	Long id;
	String nickname;
	String profileImageUrl;
}
