package com.eoe.osori.domain.member.service;

import org.apache.tomcat.util.modeler.FeatureInfo;
import org.springframework.stereotype.Service;

import com.eoe.osori.domain.member.dto.GetMemberMyPageResponseDto;
import com.eoe.osori.domain.member.repository.MemberRepository;
import com.eoe.osori.global.advice.error.exception.MemberException;
import com.eoe.osori.global.advice.error.info.MemberErrorInfo;
import com.eoe.osori.global.common.api.auth.AuthApi;
import com.eoe.osori.global.common.api.auth.dto.PostAuthInfoResponseDto;
import com.eoe.osori.global.common.response.EnvelopeResponse;

import feign.FeignException;
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
		log.info("내 정보 조회 : {}", accessToken);
		// 입력값 검증
		EnvelopeResponse<PostAuthInfoResponseDto> postAuthInfoResponseDtoEnvelopeResponse;

		try{
			postAuthInfoResponseDtoEnvelopeResponse = authApi.getMyInfo(accessToken);
		} catch (FeignException e){
			System.out.println(e.getMessage());
			throw new MemberException(MemberErrorInfo.FAIL_TO_AUTH_FEIGN_CLIENT_REQUEST);
		}

		PostAuthInfoResponseDto postAuthInfoResponseDto = postAuthInfoResponseDtoEnvelopeResponse.getData();
		return GetMemberMyPageResponseDto.of(postAuthInfoResponseDto.getId(), postAuthInfoResponseDto.getNickname(),
			postAuthInfoResponseDto.getProfileImageUrl());
	}
}
