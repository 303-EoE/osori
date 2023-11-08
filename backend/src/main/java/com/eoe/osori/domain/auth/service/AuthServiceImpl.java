package com.eoe.osori.domain.auth.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eoe.osori.domain.auth.domain.Member;
import com.eoe.osori.domain.auth.dto.PostAuthRequestDto;
import com.eoe.osori.domain.auth.dto.PostAuthResponseDto;
import com.eoe.osori.domain.auth.repository.MemberRepository;
import com.eoe.osori.global.advice.error.exception.MemberException;
import com.eoe.osori.global.advice.error.info.MemberErrorInfo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

	private final MemberRepository memberRepository;

	/**
	 *  로그인 / 회원가입
	 *
	 * @param postAuthRequestDto PostAuthRequestDto
	 * @return PostAuthResponseDto
	 * @see MemberRepository
	 */
	@Override
	@Transactional
	public PostAuthResponseDto login(PostAuthRequestDto postAuthRequestDto) {
		String provider = postAuthRequestDto.getProvider();
		String providerId = postAuthRequestDto.getProviderId();

		// 입력값 확인
		if(provider == null || providerId == null){
			throw new MemberException(MemberErrorInfo.INVALID_AUTH_REQUEST_DATA_ERROR);
		}

		// providerId로 member 존재유무 확인
		Member member = memberRepository.findByProviderId(providerId);

		// member가 존재하지 않는다면 DB에 저장
		if(member == null){
			member = Member.from(postAuthRequestDto);
			memberRepository.save(member);
		}

		String nickname = member.getNickname();
		String accessToken = null;

		return PostAuthResponseDto.of(nickname, accessToken);
	}
}
