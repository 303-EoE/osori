package com.eoe.osori.global.common.api.kakao;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.eoe.osori.global.advice.error.exception.StoreException;
import com.eoe.osori.global.advice.error.info.StoreErrorInfo;
import com.eoe.osori.global.common.api.kakao.dto.GetDistrictRequestDto;
import com.eoe.osori.global.common.api.kakao.dto.GetDistrictResponseDto;
import com.eoe.osori.global.common.api.kakao.dto.GetKakaoDistrictResponseDto;
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
	 *  주소를 구역(시/구)로 변환하는 메서드
	 *
	 * @param getDistrictRequestDto GetDistrictRequestDto
	 * @return GetDistrictResponseDto
	 */
	public GetDistrictResponseDto getDistrict(GetDistrictRequestDto getDistrictRequestDto) {
		return GetDistrictResponseDto.from(getKakaoDistrict(getKakaoUrl(getDistrictRequestDto)));
	}

	/**
	 *  주소 변환 요청을 보낼 URL 생성
	 *
	 * @param getDistrictRequestDto GetDistrictRequestDto
	 * @return String
	 */
	private String getKakaoUrl(GetDistrictRequestDto getDistrictRequestDto) {
		return new StringBuilder().append(kakaoConstantProvider.getAddressConvertUri()).append("?")
			.append("query=").append(getDistrictRequestDto.getAddressName())
			.append("&page=").append(1).append("&size=").append(1).toString();
	}

	/**
	 *  카카오 주소 검색 통신
	 *
	 * @return GetKakaoDistrictResponseDto
	 */
	private GetKakaoDistrictResponseDto getKakaoDistrict(String kakaoUrl) {
		try {
			return WebClient.create()
				.get()
				.uri(kakaoUrl)
				.headers(header -> header.set("Authorization", "KakaoAK " + kakaoConstantProvider.getClientId()))
				.retrieve()
				.bodyToMono(GetKakaoDistrictResponseDto.class)
				.block();
		} catch (Exception e) {
			throw new StoreException(StoreErrorInfo.CANNOT_CONNECT_KAKAO_LOCAL_API);
		}
	}
}
