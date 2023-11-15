package com.eoe.osori.domain.store.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eoe.osori.domain.store.dto.GetStoreDetailResponseDto;
import com.eoe.osori.domain.store.dto.GetStoreRegisterResponseDto;
import com.eoe.osori.domain.store.dto.PostStoreRequestDto;
import com.eoe.osori.domain.store.service.StoreService;
import com.eoe.osori.global.common.response.CommonIdResponseDto;
import com.eoe.osori.global.common.response.EnvelopeResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 *  가게에 대한 Controller
 */
@RestController
@RequestMapping("/stores")
@RequiredArgsConstructor
@Slf4j
public class StoreController {
	private final StoreService storeService;

	/**
	 *  가게 정보 신규 등록
	 *
	 * @param postStoreRequestDto PostStoreRequestDto
	 * @return ResponseEntity<EnvelopeResponse < CommonIdResponseDto>>
	 * @see StoreService
	 */
	@PostMapping()
	public ResponseEntity<EnvelopeResponse<CommonIdResponseDto>> registerStore(
		@RequestBody PostStoreRequestDto postStoreRequestDto) {

		return ResponseEntity.status(HttpStatus.OK)
			.body(EnvelopeResponse.<CommonIdResponseDto>builder()
				.code(HttpStatus.OK.value())
				.data(storeService.saveStore(postStoreRequestDto))
				.build());
	}

	/**
	 *  가게 등록 여부 확인
	 *
	 * @param kakaoId String
	 * @return ResponseEntity<EnvelopeResponse < GetStoreRegisterResponseDto>>
	 * @see StoreService
	 */
	@GetMapping("/register")
	public ResponseEntity<EnvelopeResponse<GetStoreRegisterResponseDto>> checkStoreIsRegistered(
		@RequestParam("kakao_id") String kakaoId) {

		return ResponseEntity.status(HttpStatus.OK)
			.body(EnvelopeResponse.<GetStoreRegisterResponseDto>builder()
				.code(HttpStatus.OK.value())
				.data(storeService.checkStoreIsRegistered(kakaoId))
				.build());
	}

	/**
	 *  가게 상세 정보 조회
	 *
	 * @param storeId String
	 * @return ResponseEntity<EnvelopeResponse < GetStoreDetailResponseDto>>
	 * @see StoreService
	 */
	@GetMapping("/detail")
	public ResponseEntity<EnvelopeResponse<GetStoreDetailResponseDto>> getStoreDetail(
		@RequestParam("store_id") Long storeId) {

		return ResponseEntity.status(HttpStatus.OK)
			.body(EnvelopeResponse.<GetStoreDetailResponseDto>builder()
				.code(HttpStatus.OK.value())
				.data(storeService.getStoreDetail(storeId))
				.build());
	}

	// 가게 리스트로 조회
	// 데이터 중에 하나라도 0이면 store에서 처리
	// 프론트로 넘겨줄 리스트에서 제거하기
}
