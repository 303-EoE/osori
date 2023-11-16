package com.eoe.osori.domain.store.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eoe.osori.domain.store.domain.Store;
import com.eoe.osori.domain.store.dto.GetStoreDetailResponseDto;
import com.eoe.osori.domain.store.dto.GetStoreListResponseDto;
import com.eoe.osori.domain.store.dto.GetStoreRegisterResponseDto;
import com.eoe.osori.domain.store.dto.PostStoreRequestDto;
import com.eoe.osori.domain.store.dto.StoreElement;
import com.eoe.osori.domain.store.repository.StoreRepository;
import com.eoe.osori.global.advice.error.exception.StoreException;
import com.eoe.osori.global.advice.error.info.StoreErrorInfo;
import com.eoe.osori.global.common.api.kakao.KakaoApi;
import com.eoe.osori.global.common.api.kakao.dto.GetDistrictRequestDto;
import com.eoe.osori.global.common.api.review.ReviewApi;
import com.eoe.osori.global.common.api.review.dto.GetStoreReviewCacheDataResponseDto;
import com.eoe.osori.global.common.redis.domain.StoreInfo;
import com.eoe.osori.global.common.redis.repository.StoreInfoRedisRepository;
import com.eoe.osori.global.common.response.CommonIdResponseDto;
import com.eoe.osori.global.common.response.EnvelopeResponse;
import com.eoe.osori.global.meta.domain.StoreCategory;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoreServiceImpl implements StoreService {
	private final StoreRepository storeRepository;
	private final StoreInfoRedisRepository storeInfoRedisRepository;
	private final KakaoApi kakaoApi;
	private final ReviewApi reviewApi;

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

		Optional<Store> optionalStore = storeRepository.findByKakaoId(postStoreRequestDto.getKakaoId());

		if (optionalStore.isPresent()) {
			return CommonIdResponseDto.from(optionalStore.get().getId());
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
	 * @see StoreInfoRedisRepository
	 */
	@Override
	public GetStoreDetailResponseDto getStoreDetail(Long id) {
		Store store = storeRepository.findById(id)
			.orElseThrow(() -> new StoreException(StoreErrorInfo.CANNOT_FIND_STORE_BY_ID));

		// redis cache에 있으면 cacahe에서 가져오기
		Optional<StoreInfo> optionalStoreInfo = storeInfoRedisRepository.findById(id);

		if (optionalStoreInfo.isPresent()) {
			storeInfoRedisRepository.save(optionalStoreInfo.get());
			return GetStoreDetailResponseDto.of(store, optionalStoreInfo.get());
		}

		// cache에 없으면 Feign으로 review에서 가져오고 redis에 저장하기
		EnvelopeResponse<GetStoreReviewCacheDataResponseDto> getStoreReviewCacheDataResponseDtoEnvelopeResponse;

		try {
			getStoreReviewCacheDataResponseDtoEnvelopeResponse = reviewApi.getReviewCacheDataByStore(id,
				store.getCategory().getDefaultBillType().getName());
		} catch (FeignException e) {
			throw new StoreException(StoreErrorInfo.FAIL_TO_REVIEW_FEIGN_CLIENT_REQUEST);
		}

		StoreInfo storeInfo = StoreInfo.from(getStoreReviewCacheDataResponseDtoEnvelopeResponse.getData());

		storeInfoRedisRepository.save(storeInfo);

		return GetStoreDetailResponseDto.of(store, storeInfo);
	}

	/**
	 *  지역으로 가게 목록 조회
	 *
	 * @param depth1 String
	 * @param depth2 String
	 * @return GetStoreListResponseDto
	 * @see StoreRepository
	 * @see ReviewApi
	 */
	@Transactional
	@Override
	public GetStoreListResponseDto getStoreListByRegion(String depth1, String depth2) {
		List<Store> storeList = storeRepository.findAllByDepth1AndDepth2(depth1, depth2);

		GetStoreListResponseDto getStoreListResponseDto = new GetStoreListResponseDto();
		Integer restaurantTotalPrice = 0;
		Integer restaurantCount = 0;
		Integer fitnessCenterTotalPrice = 0;
		Integer fitnessCenterCount = 0;
		Integer nailShopTotalPrice = 0;
		Integer nailShopCount = 0;

		for (Store store : storeList) {
			Optional<StoreInfo> optionalStoreInfo = storeInfoRedisRepository.findById(store.getId());
			StoreInfo storeInfo = null;

			if (optionalStoreInfo.isPresent()) {
				storeInfo = optionalStoreInfo.get();
			}

			if (storeInfo == null) {
				EnvelopeResponse<GetStoreReviewCacheDataResponseDto> getStoreReviewCacheDataResponseDtoEnvelopeResponse;

				try {
					getStoreReviewCacheDataResponseDtoEnvelopeResponse = reviewApi.getReviewCacheDataByStore(
						store.getId(), store.getCategory().getDefaultBillType().getName());
				} catch (FeignException e) {
					throw new StoreException(StoreErrorInfo.FAIL_TO_REVIEW_FEIGN_CLIENT_REQUEST);
				}

				storeInfo = StoreInfo.from(getStoreReviewCacheDataResponseDtoEnvelopeResponse.getData());
			}

			storeInfoRedisRepository.save(storeInfo);

			if (storeInfo.getBillTypeTotalReviewCount().equals(0)) {
				continue;
			}

			getStoreListResponseDto.getStores().add(StoreElement.of(store, storeInfo));

			if (store.getCategory().equals(StoreCategory.RESTAURANT)) {
				restaurantTotalPrice += storeInfo.getAveragePrice();
				restaurantCount++;
			}
			if (store.getCategory().equals(StoreCategory.FITNESS_CENTER)) {
				fitnessCenterTotalPrice += storeInfo.getAveragePrice();
				fitnessCenterCount++;
			}
			if (store.getCategory().equals(StoreCategory.NAIL_SHOP)) {
				nailShopTotalPrice += storeInfo.getAveragePrice();
				nailShopCount++;
			}
		}

		if (restaurantCount != 0) {
			getStoreListResponseDto.updateRestaurantAveragePrice(restaurantTotalPrice / restaurantCount);
		}
		if (fitnessCenterCount != 0) {
			getStoreListResponseDto.updateFitnessCenterAveragePrice(fitnessCenterTotalPrice / fitnessCenterCount);
		}
		if (nailShopCount != 0) {
			getStoreListResponseDto.updateNailShopAveragePrice(nailShopTotalPrice / nailShopCount);
		}

		return getStoreListResponseDto;
	}
}
