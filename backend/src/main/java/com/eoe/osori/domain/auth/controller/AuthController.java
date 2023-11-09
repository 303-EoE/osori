package com.eoe.osori.domain.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eoe.osori.domain.auth.dto.PostAuthRequestDto;
import com.eoe.osori.domain.auth.dto.PostAuthResponseDto;
import com.eoe.osori.domain.auth.service.AuthService;
import com.eoe.osori.global.common.response.EnvelopeResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

	private final AuthService authService;

	/**
	 *  로그인 / 회원가입
	 *
	 * @param postAuthRequestDto PostAuthRequestDto
	 * @return ResponseEntity<EnvelopeResponse<PostAuthResponseDto>>
	 * @see AuthService
	 */
	@PostMapping
	public ResponseEntity<EnvelopeResponse<PostAuthResponseDto>> login(@RequestBody PostAuthRequestDto postAuthRequestDto) {
		return ResponseEntity.status(HttpStatus.OK)
			.body(EnvelopeResponse.<PostAuthResponseDto>builder()
				.code(HttpStatus.OK.value())
				.data(authService.login(postAuthRequestDto))
				.build());
	}

}
