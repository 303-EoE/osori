package com.eoe.osori.domain.store.service;

import com.eoe.osori.domain.store.dto.PostStoreRequestDto;
import com.eoe.osori.global.common.response.CommonIdResponseDto;

public interface StoreService {
	CommonIdResponseDto saveStore(PostStoreRequestDto postStoreRequestDto);
}
