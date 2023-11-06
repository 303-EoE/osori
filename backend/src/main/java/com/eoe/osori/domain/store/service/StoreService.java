package com.eoe.osori.domain.store.service;

import com.eoe.osori.domain.store.dto.GetStoreDetailResponseDto;
import com.eoe.osori.domain.store.dto.GetStoreRegisterResponseDto;
import com.eoe.osori.domain.store.dto.PostStoreRequestDto;
import com.eoe.osori.global.common.response.CommonIdResponseDto;

public interface StoreService {
	CommonIdResponseDto saveStore(PostStoreRequestDto postStoreRequestDto);

	GetStoreRegisterResponseDto checkStoreIsRegistered(String kakaoId);

	GetStoreDetailResponseDto getStoreDetail(Long id);
}
