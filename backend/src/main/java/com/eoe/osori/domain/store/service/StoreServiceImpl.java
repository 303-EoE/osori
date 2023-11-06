package com.eoe.osori.domain.store.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eoe.osori.domain.store.domain.Store;
import com.eoe.osori.domain.store.dto.GetStoreDetailResponseDto;
import com.eoe.osori.domain.store.dto.GetStoreRegisterResponseDto;
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

	/**
	 *  카카오 아이디를 기준으로 가게 등록 여부를 확인하는 메서드
	 *
	 * @param kakaoId String
	 * @return GetStoreRegisterResponseDto
	 * @see StoreRepository
	 */
	@Override
	public GetStoreRegisterResponseDto checkStoreIsRegistered(String kakaoId) {
		if (!storeRepository.existsByKakaoId(kakaoId)) {
			return GetStoreRegisterResponseDto.of(false, null);
		}

		Store store = storeRepository.findByKakaoId(kakaoId)
			.orElseThrow(() -> new StoreException(StoreErrorInfo.CANNOT_FIND_STORE_BY_KAKAO_ID));

		return GetStoreRegisterResponseDto.of(true, store.getId());
	}

	/**
	 *  가게 상세 정보를 조회하는 메서드
	 *
	 * @param id Long
	 * @return GetStoreDetailResponseDto
	 * @see StoreRepository
	 */
	@Override
	public GetStoreDetailResponseDto getStoreDetail(Long id) {
		Store store = storeRepository.findById(id)
			.orElseThrow(() -> new StoreException(StoreErrorInfo.CANNOT_FIND_STORE_BY_ID));

		// Feign + redis 로직 추가

		return GetStoreDetailResponseDto.from(store);
	}
}
