package com.eoe.osori.domain.auth.dto;

import com.eoe.osori.domain.auth.domain.redis.Token;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PostAuthReissueTokenResponseDto {

    private String accessToken;
    private String refreshToken;

    public static PostAuthReissueTokenResponseDto from(Token token) {
        return PostAuthReissueTokenResponseDto.builder()
                .accessToken(token.getAccessToken())
                .refreshToken(token.getRefreshToken())
                .build();
    }
}
