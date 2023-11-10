package com.eoe.osori.domain.auth.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eoe.osori.domain.auth.domain.Member;
import com.eoe.osori.domain.auth.dto.PostAuthInfoRequestDto;
import com.eoe.osori.domain.auth.dto.PostAuthInfoResponseDto;
import com.eoe.osori.domain.auth.dto.PostAuthLoginRequestDto;
import com.eoe.osori.domain.auth.dto.PostAuthLoginResponseDto;
import com.eoe.osori.domain.auth.repository.MemberRepository;
import com.eoe.osori.global.advice.error.exception.MemberException;
import com.eoe.osori.global.advice.error.info.MemberErrorInfo;
import com.eoe.osori.global.common.jwt.JwtHeaderUtilEnum;
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
	 * @param postAuthLoginRequestDto PostAuthRequestDto
	 * @return PostAuthResponseDto
	 * @see MemberRepository
	 */
	@Override
	@Transactional
	public PostAuthLoginResponseDto login(PostAuthLoginRequestDto postAuthLoginRequestDto) {
		String provider = postAuthLoginRequestDto.getProvider();
		String providerId = postAuthLoginRequestDto.getProviderId();

		// 입력값 확인
		if(provider == null || providerId == null){
			throw new MemberException(MemberErrorInfo.INVALID_AUTH_REQUEST_DATA_ERROR);
		}

		// providerId로 member 존재유무 확인
		Member member = memberRepository.findByProviderId(providerId)
			.orElse(Member.from(postAuthLoginRequestDto));

		// member가 존재하지 않는다면 DB에 저장
		if(member.getId() == null){
			memberRepository.save(member);
			log.info("[유저 로그인] 가입전적 없음.");
		}

		String nickname = member.getNickname();

		String accessToken = null;
		String refreshToken = null;

		// 닉네임이 설정되어 있다면 가입 전적이 있는 사용자
		if(nickname != null){
			accessToken = jwtTokenProvider.generateAccessToken(member.getId());
			refreshToken = jwtTokenProvider.generateRefreshToken(member.getId());
		}

		return PostAuthLoginResponseDto.of(nickname, accessToken, refreshToken);
	}

	/**
	 * 토큰이 헤더에서 들어올 때 파싱
	 * @param accessToken
	 * @return
	 */
	private String parsingAccessToken(String accessToken){
		return accessToken.substring(JwtHeaderUtilEnum.GRANT_TYPE.getValue().length());
	}

	@Override
	@Transactional
	public PostAuthInfoResponseDto info(PostAuthInfoRequestDto postAuthInfoRequestDto) {
		// String accessToken = parsingAccessToken(postAuthInfoRequestDto.getAccessToken());
		String accessToken = postAuthInfoRequestDto.getAccessToken();

		// 토큰에서 id 가져오기
		Long id = jwtTokenProvider.getLoginId(accessToken);

		// id로 멤버 찾기
		Member member = memberRepository.findById(id)
			.orElseThrow(() -> new MemberException(MemberErrorInfo.MEMBER_NOT_FOUND));

		return PostAuthInfoResponseDto.builder()
			.id(id)
			.nickname(member.getNickname())
			.profileImageUrl(member.getProfileImageUrl())
			.build();
	}

}
