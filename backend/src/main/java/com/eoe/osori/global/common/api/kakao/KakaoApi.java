package com.eoe.osori.global.common.api.kakao;

import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.eoe.osori.global.common.api.kakao.service.KakaoConstantProvider;

import lombok.RequiredArgsConstructor;

/**
 * 카카오 API 연동 함수
 *
 * @see KakaoConstantProvider
 */
@Component
@RequiredArgsConstructor
public class KakaoApi {
	private final KakaoConstantProvider kakaoConstantProvider;

	/**
	 *  토큰으로 사용자 정보 조회
	 *
	 * @param accessToken String
	 * @return 카카오에서 조회된 사용자 정보
	 */
	// public PostLoginRequestDto getKakaoProfile(String accessToken) {
	// 	try {
	// 		return WebClient.create()
	// 			.get()
	// 			.uri(kakaoConstantProvider.getAddressConvertUri())
	// 			.headers(header -> header.setBearerAuth(accessToken))
	// 			.retrieve()
	// 			.bodyToMono(PostLoginRequestDto.class)
	// 			.block();
	// 	} catch (Exception e) {
	// 		throw new AuthException(AuthErrorInfo.AUTH_SERVER_ERROR);
	// 	}
	// }

	/**
	 *  주소 변환에 필요한 파라미터 세팅
	 *
	 * @param code String
	 * @return MultiValueMap<String, String>
	 */
	// private MultiValueMap<String, String> setConvertAddressParameters(String code) {
	// 	MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
	//
	// 	params.add("grant_type", "authorization_code");
	// 	params.add("client_id", authConstantProvider.getClientId());
	// 	params.add("client_secret", authConstantProvider.getClientSecret());
	// 	params.add("redirect_uri", authConstantProvider.getLoginRedirectUri());
	// 	params.add("code", code);
	//
	// 	return params;
	// }
}
