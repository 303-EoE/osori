package com.eoe.osori.domain.member.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.eoe.osori.domain.member.dto.GetMemberResponseDto;
import com.eoe.osori.domain.member.dto.PatchMemberRequestDto;
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

	@GetMapping
	ResponseEntity<EnvelopeResponse<GetMemberResponseDto>> getMemberProfile(
		@RequestParam("member_id") Long memberId
	){
		return ResponseEntity.status(HttpStatus.OK)
			.body(EnvelopeResponse.<GetMemberResponseDto>builder()
				.code(HttpStatus.OK.value())
				.data(memberService.getMemberInfo(memberId))
				.build());
	}

	@PutMapping
	ResponseEntity<EnvelopeResponse<Void>> updateMyProfile(
		@RequestPart(value = "patchMemberRequestDto") PatchMemberRequestDto patchMemberRequestDto,
		@RequestPart(value = "profileImage", required =false) List<MultipartFile> profileImage){
		System.out.println("patchMemberRequestDto.getNickname() = " + patchMemberRequestDto.getNickname());
		memberService.updateProfile(patchMemberRequestDto, profileImage);
		return ResponseEntity.status(HttpStatus.OK)
			.body(EnvelopeResponse.<Void>builder()
				.code(HttpStatus.OK.value())
				.build());
	}
}
