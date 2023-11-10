package com.eoe.osori.domain.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eoe.osori.domain.auth.dto.PostAuthInfoRequestDto;
import com.eoe.osori.domain.auth.dto.PostAuthInfoResponseDto;
import com.eoe.osori.domain.auth.dto.PostAuthLoginRequestDto;
import com.eoe.osori.domain.auth.dto.PostAuthLoginResponseDto;
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
	 * @return
	 */
	@PostMapping("/info")
	public ResponseEntity<EnvelopeResponse<PostAuthInfoResponseDto>> info(@RequestBody PostAuthInfoRequestDto postAuthInfoRequestDto){
		return ResponseEntity.status(HttpStatus.OK)
			.body(EnvelopeResponse.<PostAuthInfoResponseDto>builder()
				.code(HttpStatus.OK.value())
				.data(authService.info(postAuthInfoRequestDto))
				.build());
	}
}
