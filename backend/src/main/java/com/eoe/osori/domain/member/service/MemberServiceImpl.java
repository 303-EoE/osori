package com.eoe.osori.domain.member.service;

import org.springframework.stereotype.Service;

import com.eoe.osori.domain.member.dto.GetMemberMyPageResponseDto;
import com.eoe.osori.domain.member.repository.MemberRepository;
import com.eoe.osori.global.advice.error.exception.MemberException;
import com.eoe.osori.global.advice.error.info.MemberErrorInfo;
import com.eoe.osori.global.common.api.auth.AuthApi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService{

	private final MemberRepository memberRepository;
	private final AuthApi authApi;
	@Override
	public GetMemberMyPageResponseDto getMyInfo(String accessToken) {
		// 입력값 검증


		return null;
	}
}
