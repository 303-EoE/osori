package com.eoe.osori.domain.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PostAuthReissueTokenResponseDto {

	private String accessToken;
	private String refreshToken;
}
