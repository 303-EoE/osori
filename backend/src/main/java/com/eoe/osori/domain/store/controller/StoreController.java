package com.eoe.osori.domain.store.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	 * @return ResponseEntity<EnvelopeResponse<CommonIdResponseDto>>
	 * @see StoreService
	 */
	@PostMapping
	public ResponseEntity<EnvelopeResponse<CommonIdResponseDto>> registerStore(@RequestBody PostStoreRequestDto postStoreRequestDto) {

		return ResponseEntity.status(HttpStatus.OK)
			.body(EnvelopeResponse.<CommonIdResponseDto>builder()
				.code(HttpStatus.OK.value())
				.data(storeService.saveStore(postStoreRequestDto))
				.build());
	}
}
