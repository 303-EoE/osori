package com.eoe.osori.domain.auth.service;

import com.eoe.osori.domain.auth.dto.PostAuthRequestDto;
import com.eoe.osori.domain.auth.dto.PostAuthResponseDto;

public interface AuthService {

	PostAuthResponseDto login(PostAuthRequestDto postAuthRequestDto);
}
