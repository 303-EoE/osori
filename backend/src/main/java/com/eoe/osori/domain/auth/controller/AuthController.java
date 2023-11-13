package com.eoe.osori.domain.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.eoe.osori.domain.auth.dto.PostAuthInfoRequestDto;
import com.eoe.osori.domain.auth.dto.PostAuthInfoResponseDto;
import com.eoe.osori.domain.auth.dto.PostAuthLoginRequestDto;
import com.eoe.osori.domain.auth.dto.PostAuthLoginResponseDto;
import com.eoe.osori.domain.auth.dto.PostAuthProfileRequestDto;
import com.eoe.osori.domain.auth.service.AuthService;
import com.eoe.osori.global.common.response.EnvelopeResponse;
import com.eoe.osori.global.common.security.SecurityUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

	private final AuthService authService;
	private final SecurityUtils securityUtils;

	/**
	 *  로그인 / 회원가입
	 *
	 * @param postAuthLoginRequestDto PostAuthRequestDto
	 * @return ResponseEntity<EnvelopeResponse<PostAuthResponseDto>>
	 * @see AuthService
	 */
	@PostMapping("/login")
	public ResponseEntity<EnvelopeResponse<PostAuthLoginResponseDto>> login(@RequestBody PostAuthLoginRequestDto postAuthLoginRequestDto) {
		return ResponseEntity.status(HttpStatus.OK)
			.body(EnvelopeResponse.<PostAuthLoginResponseDto>builder()
				.code(HttpStatus.OK.value())
				.data(authService.login(postAuthLoginRequestDto))
				.build());
	}

	/**
	 * 토큰으로 회원 정보 조회
	 * @param postAuthInfoRequestDto
	 * @return PostAuthInfoResponseDto
	 * @see AuthService
	 */
	@PostMapping("/info")
	public ResponseEntity<EnvelopeResponse<PostAuthInfoResponseDto>> info(@RequestBody PostAuthInfoRequestDto postAuthInfoRequestDto){
		return ResponseEntity.status(HttpStatus.OK)
			.body(EnvelopeResponse.<PostAuthInfoResponseDto>builder()
				.code(HttpStatus.OK.value())
				.data(authService.getLoginUserInfo(postAuthInfoRequestDto))
				.build());
	}

	/**
	 * 회원 가입 시 회원 정보 등록
	 * @param accessToken String
	 * @param postAuthProfileRequestDto PostAuthProfileRequestDto
	 * @param profileImage MultipartFile
	 * @return
	 * @see AuthService
	 */
	@PostMapping("/profile")
	public ResponseEntity<EnvelopeResponse<Void>> profile(
		@RequestHeader("Authorization") String accessToken,
		@RequestPart(value = "postAuthProfileRequestDto") PostAuthProfileRequestDto postAuthProfileRequestDto,
		@RequestPart(value = "profileImage", required = false) MultipartFile profileImage){
		authService.saveProfile(accessToken, postAuthProfileRequestDto, profileImage);
		return ResponseEntity.status(HttpStatus.OK)
			.body(EnvelopeResponse.<Void>builder()
				.code(HttpStatus.OK.value())
				.build());
	}
}
