package com.eoe.osori.domain.auth.service;

import org.springframework.web.multipart.MultipartFile;

import com.eoe.osori.domain.auth.dto.PostAuthInfoRequestDto;
import com.eoe.osori.domain.auth.dto.PostAuthInfoResponseDto;
import com.eoe.osori.domain.auth.dto.PostAuthLoginRequestDto;
import com.eoe.osori.domain.auth.dto.PostAuthLoginResponseDto;
import com.eoe.osori.domain.auth.dto.PostAuthProfileRequestDto;

public interface AuthService {

	PostAuthLoginResponseDto login(PostAuthLoginRequestDto postAuthLoginRequestDto);
	PostAuthInfoResponseDto getLoginUserInfo(PostAuthInfoRequestDto postAuthInfoRequestDto);

	void saveProfile(String accessToken, PostAuthProfileRequestDto postAuthProfileRequestDto, MultipartFile profileImage);
}
