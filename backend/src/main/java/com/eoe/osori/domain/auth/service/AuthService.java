package com.eoe.osori.domain.auth.service;

import com.eoe.osori.domain.auth.dto.PostAuthInfoRequestDto;
import com.eoe.osori.domain.auth.dto.PostAuthInfoResponseDto;
import com.eoe.osori.domain.auth.dto.PostAuthLoginRequestDto;
import com.eoe.osori.domain.auth.dto.PostAuthLoginResponseDto;

public interface AuthService {

	PostAuthLoginResponseDto login(PostAuthLoginRequestDto postAuthLoginRequestDto);
	PostAuthInfoResponseDto info(PostAuthInfoRequestDto postAuthInfoRequestDto);
}
