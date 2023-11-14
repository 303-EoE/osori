package com.eoe.osori.domain.auth.service;

import org.springframework.web.multipart.MultipartFile;

import com.eoe.osori.domain.auth.dto.PostAuthInfoRequestDto;
import com.eoe.osori.domain.auth.dto.PostAuthInfoResponseDto;
import com.eoe.osori.domain.auth.dto.PostAuthLoginRequestDto;
import com.eoe.osori.domain.auth.dto.PostAuthLoginResponseDto;
import com.eoe.osori.domain.auth.dto.PostAuthProfileRequestDto;
import com.eoe.osori.domain.auth.dto.PostAuthProfileResponseDto;
import com.eoe.osori.domain.auth.dto.PostAuthReissueTokenResponseDto;

public interface AuthService {

	PostAuthLoginResponseDto login(PostAuthLoginRequestDto postAuthLoginRequestDto);
	PostAuthInfoResponseDto getLoginUserInfo(String accessToken);

	PostAuthProfileResponseDto saveProfile(PostAuthProfileRequestDto postAuthProfileRequestDto, MultipartFile profileImage);
}
