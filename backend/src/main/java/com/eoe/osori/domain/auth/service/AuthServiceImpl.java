package com.eoe.osori.domain.auth.service;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eoe.osori.domain.auth.domain.Member;
import com.eoe.osori.domain.auth.dto.PostAuthRequestDto;
import com.eoe.osori.domain.auth.dto.PostAuthResponseDto;
import com.eoe.osori.domain.auth.repository.MemberRepository;
import com.eoe.osori.global.advice.error.exception.MemberException;
import com.eoe.osori.global.advice.error.info.MemberErrorInfo;
import com.eoe.osori.global.common.jwt.JwtTokenProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

	private final MemberRepository memberRepository;
	private final JwtTokenProvider jwtTokenProvider;


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
		log.info("[유저 로그인] 로그인 요청. {}", providerId);

		// 입력값 확인
		if(provider == null || providerId == null){
			throw new MemberException(MemberErrorInfo.INVALID_AUTH_REQUEST_DATA_ERROR);
		}

		// providerId로 member 존재유무 확인
		Member member = memberRepository.findByProviderId(providerId)
			.orElse(Member.from(postAuthRequestDto));

		// member가 존재하지 않는다면 DB에 저장
		if(member.getId() == null){
			memberRepository.save(member);
			log.info("[유저 로그인] 가입전적 없음.");
		}

		String nickname = member.getNickname();
		log.info("[유저 로그인] 사용자 닉네임 : {}", nickname);

		String accessToken = null;
		String refreshToken = null;

		// 닉네임이 설정되어 있다면 가입 전적이 있는 사용자
		if(nickname != null){
			accessToken = jwtTokenProvider.generateAccessToken(member.getId());
			refreshToken = jwtTokenProvider.generateRefreshToken(member.getId());
		}

		log.info("[유저 로그인] 엑세스 토큰 : {}", accessToken);

		return PostAuthResponseDto.of(nickname, accessToken, refreshToken);
	}
}
