package com.eoe.osori.domain.store.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eoe.osori.domain.store.domain.Store;
import com.eoe.osori.domain.store.dto.PostStoreRequestDto;
import com.eoe.osori.domain.store.repository.StoreRepository;
import com.eoe.osori.global.advice.error.exception.StoreException;
import com.eoe.osori.global.advice.error.info.StoreErrorInfo;
import com.eoe.osori.global.common.api.kakao.KakaoApi;
import com.eoe.osori.global.common.api.kakao.dto.GetDistrictRequestDto;
import com.eoe.osori.global.common.response.CommonIdResponseDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoreServiceImpl implements StoreService {
	private final StoreRepository storeRepository;
	private final KakaoApi kakaoApi;

	/**
	 *  가게 정보를 저장하는 메서드
	 *
	 * @param postStoreRequestDto PostStoreRequestDto
	 * @return CommonIdResponseDto
	 * @see StoreRepository
	 * @see KakaoApi
	 */
	@Transactional
	@Override
	public CommonIdResponseDto saveStore(PostStoreRequestDto postStoreRequestDto) {
		if (postStoreRequestDto.findEmptyValue()) {
			throw new StoreException(StoreErrorInfo.INVALID_STORE_REQUEST_DATA_ERROR);
		}

		if (storeRepository.existsByKakaoId(postStoreRequestDto.getKakaoId())) {
			throw new StoreException(StoreErrorInfo.ALREADY_REGISTERED_KAKAO_ID);
		}

		Store store = Store.of(postStoreRequestDto,
			kakaoApi.getDistrict(GetDistrictRequestDto.from(postStoreRequestDto)));

		storeRepository.save(store);

		return CommonIdResponseDto.from(store.getId());
	}
}
