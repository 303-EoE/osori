package com.eoe.osori.domain.auth.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.eoe.osori.domain.auth.domain.Member;
import com.eoe.osori.domain.auth.dto.PostAuthInfoRequestDto;
import com.eoe.osori.domain.auth.dto.PostAuthInfoResponseDto;
import com.eoe.osori.domain.auth.dto.PostAuthLoginRequestDto;
import com.eoe.osori.domain.auth.dto.PostAuthLoginResponseDto;
import com.eoe.osori.domain.auth.dto.PostAuthProfileRequestDto;
import com.eoe.osori.domain.auth.dto.PostAuthProfileResponseDto;
import com.eoe.osori.domain.auth.repository.MemberRepository;
import com.eoe.osori.global.advice.error.exception.AuthException;
import com.eoe.osori.global.advice.error.info.AuthErrorInfo;
import com.eoe.osori.global.common.api.images.ImageApi;
import com.eoe.osori.global.common.api.images.dto.PostImageResponseDto;
import com.eoe.osori.global.common.jwt.JwtHeaderUtilEnum;
import com.eoe.osori.global.common.jwt.JwtTokenProvider;
import com.eoe.osori.global.common.response.EnvelopeResponse;

import feign.FeignException;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

	private final MemberRepository memberRepository;
	private final JwtTokenProvider jwtTokenProvider;
	private final ImageApi imageApi;


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
			throw new AuthException(AuthErrorInfo.INVALID_AUTH_REQUEST_DATA_ERROR);
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

	/**
	 * 토큰에서 로그인 유저 정보 조회
	 * @param accessToken String
	 * @return PostAuthInfoResponseDto
	 */
	@Override
	@Transactional
	public PostAuthInfoResponseDto getLoginUserInfo(String accessToken) {
		int startIndex = accessToken.indexOf("Bearer") + "Bearer".length() + 1;
		int endIndex = accessToken.lastIndexOf("\"");
		String token = accessToken.substring(startIndex, endIndex);
		if(token == null){
			throw new AuthException(AuthErrorInfo.INVALID_AUTH_REQUEST_DATA_ERROR);
		}
		// 토큰에서 id 가져오기
		Long id = jwtTokenProvider.getLoginId(token);

		// id로 멤버 찾기
		Member member = memberRepository.findById(id)
			.orElseThrow(() -> new AuthException(AuthErrorInfo.MEMBER_NOT_FOUND));

		return PostAuthInfoResponseDto.of(id, member.getNickname(), member.getProfileImageUrl());
	}

	/**
	 * 회원 가입시 회원 정보 등록
	 * @param postAuthProfileRequestDto PostAuthProfileRequestDto
	 * @param profileImage MultipartFile
	 * @return PostAuthProfileResponseDto
	 * @see ImageApi
	 * @see MemberRepository
	 */
	@Override
	@Transactional
	public PostAuthProfileResponseDto saveProfile(PostAuthProfileRequestDto postAuthProfileRequestDto, MultipartFile profileImage) {
		// providerId 유효성 검사
		String providerId = postAuthProfileRequestDto.getProviderId();
		if(StringUtils.isBlank(providerId)){
			throw new AuthException(AuthErrorInfo.INVALID_AUTH_REQUEST_DATA_ERROR);
		}

		// providerId로 멤버 찾기
		Member member = memberRepository.findByProviderId(providerId)
			.orElseThrow(() -> new AuthException(AuthErrorInfo.MEMBER_NOT_FOUND));

		// 닉네임 유효성 검사
		String nickname = postAuthProfileRequestDto.getNickname();
		if(StringUtils.isBlank(nickname)){
			throw new AuthException(AuthErrorInfo.INVALID_AUTH_REQUEST_DATA_ERROR);
		}
		// 닉네임 중복 검사
		Optional<Member> exist = memberRepository.findByNickname(nickname);
		if(exist.isPresent() && member.getId() != exist.get().getId()){
			throw new AuthException(AuthErrorInfo.EXIST_MEMBER_NICKNAME);
		}

		String profileImageUrl = null;

		// 이미지 업데이트 할 때 null이 아니면 새로 들어온 이미지 저장
		if(profileImage != null){
			EnvelopeResponse<PostImageResponseDto> postImageResponseDtoEnvelopeResponse;
			List<MultipartFile> profileImages = new ArrayList<>();
			profileImages.add(profileImage);

			try{
				postImageResponseDtoEnvelopeResponse = imageApi.getProfileImages(profileImages);
			} catch (FeignException e) {
				System.out.println(e.getMessage());
				throw new AuthException(AuthErrorInfo.FAIL_TO_IMAGE_FEIGN_CLIENT_REQUEST);
			}

			PostImageResponseDto postImageResponseDto = postImageResponseDtoEnvelopeResponse.getData();

			profileImageUrl = postImageResponseDto.getPath().get(0).getUploadFilePath();

		}
		// 멤버 정보 수정
		member.updateMember(nickname, profileImageUrl);

		// 토큰 발급
		String accessToken = jwtTokenProvider.generateAccessToken(member.getId());
		String refreshToken = jwtTokenProvider.generateRefreshToken(member.getId());

		return PostAuthProfileResponseDto.of(nickname, accessToken, refreshToken);
	}

}
