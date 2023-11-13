package com.eoe.osori.domain.member.controller;

import java.net.Authenticator;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eoe.osori.domain.member.dto.GetMemberMyPageResponseDto;
import com.eoe.osori.domain.member.service.MemberService;
import com.eoe.osori.global.common.response.EnvelopeResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

	private final MemberService memberService;

	@GetMapping("/my-page")
	ResponseEntity<EnvelopeResponse<GetMemberMyPageResponseDto>> getMyProfile(
		@RequestHeader("Authorization") String accessToken
	){
		return ResponseEntity.status(HttpStatus.OK)
			.body(EnvelopeResponse.<GetMemberMyPageResponseDto>builder()
				.code(HttpStatus.OK.value())
				.data(memberService.getMyInfo(accessToken))
				.build());
	}
}
