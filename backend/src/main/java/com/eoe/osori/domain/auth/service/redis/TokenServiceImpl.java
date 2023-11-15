package com.eoe.osori.domain.auth.service.redis;


import com.eoe.osori.domain.auth.domain.redis.Token;
import com.eoe.osori.domain.auth.dto.PostAuthReissueTokenResponseDto;
import com.eoe.osori.domain.auth.repository.redis.TokenRepository;
import com.eoe.osori.global.advice.error.exception.AuthException;
import com.eoe.osori.global.advice.error.info.AuthErrorInfo;
import com.eoe.osori.global.common.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;

    /**
     * Redis에 토큰 저장
     *
     * @param refreshToken String
     * @param accessToken  String
     * @see TokenRepository
     */
    @Transactional
    @Override
    public void saveTokenInfo(String refreshToken, String accessToken) {
        tokenRepository.save(new Token(accessToken, refreshToken));
    }

    /**
     * refreshToken 제거 , 로그아웃
     *
     * @param accessToken String
     * @see TokenRepository
     */
    @Transactional
    @Override
    public void removeRefreshToken(String accessToken) {

        // 토큰 파싱
        int startIndex = accessToken.indexOf("Bearer") + "Bearer".length() + 1;
        int endIndex = accessToken.length();
        accessToken = accessToken.substring(startIndex, endIndex);

        Token token = tokenRepository.findByAccessToken(accessToken)
                .orElseThrow(() -> new AuthException(AuthErrorInfo.TOKEN_NOT_FOUND));

        tokenRepository.delete(token);
    }

    /**
     * refreshToken으로 accessToken 재발급
     * 
     * @param refreshToken String
     * @return PostAuthReissueTokenResponseDto
     * @see TokenRepository
     */
    @Transactional
    @Override
    public PostAuthReissueTokenResponseDto reissueAccessToken(String refreshToken) {

        // 토큰 파싱
        int startIndex = refreshToken.indexOf("Bearer") + "Bearer".length() + 1;
        int endIndex = refreshToken.length();
        refreshToken = refreshToken.substring(startIndex, endIndex);

        // 액세스 토큰으로 Refresh 토큰 객체를 조회
        Token token = tokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(()-> new AuthException(AuthErrorInfo.TOKEN_NOT_FOUND));

        Long id = jwtTokenProvider.getLoginId(token.getRefreshToken());

        UserDetails userDetails = userDetailsService.loadUserByUsername(id.toString());

        // RefreshToken이 존재하고 유효하다면 실행
        if (jwtTokenProvider.validateToken(token.getRefreshToken(), userDetails)) {

            // 권한과 아이디를 추출해 새로운 액세스토큰을 만든다.
            String newAccessToken = jwtTokenProvider.generateAccessToken(id);
            // 액세스 토큰의 값을 수정해준다.
            token.updateAccessToken(newAccessToken);
            tokenRepository.save(token);
        } else {
            throw new AuthException(AuthErrorInfo.EXPIRED_REFRESH_TOKEN);
        }

        return PostAuthReissueTokenResponseDto.from(token);
    }

    /**
     * AccessToken으로 토큰 조회
     *
     * @param accessToken String
     * @return Token
     * @see TokenRepository
     */
    @Override
    public Token getTokenByAccessToken(String accessToken) {
        Token token = tokenRepository.findByAccessToken(accessToken)
                .orElseThrow(() -> new AuthException(AuthErrorInfo.TOKEN_NOT_FOUND));
        return token;
    }
}
