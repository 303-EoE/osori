package com.eoe.osori.domain.auth.service.redis;

import com.eoe.osori.domain.auth.domain.redis.Token;
import com.eoe.osori.domain.auth.dto.PostAuthReissueTokenResponseDto;

public interface TokenService {

    void saveTokenInfo(String refreshToken, String accessToken);

    void removeRefreshToken(String accessToken);

    PostAuthReissueTokenResponseDto reissueAccessToken(String refreshToken);

    Token getTokenByAccessToken(String accessToken);

}
