package com.eoe.osori.domain.member.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.tomcat.util.modeler.FeatureInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.eoe.osori.domain.member.domain.Member;
import com.eoe.osori.domain.member.dto.GetMemberMyPageResponseDto;
import com.eoe.osori.domain.member.dto.GetMemberResponseDto;
import com.eoe.osori.domain.member.repository.MemberRepository;
import com.eoe.osori.global.advice.error.exception.MemberException;
import com.eoe.osori.global.advice.error.info.MemberErrorInfo;
import com.eoe.osori.global.common.api.auth.AuthApi;
import com.eoe.osori.global.common.api.auth.dto.PostAuthInfoResponseDto;
import com.eoe.osori.global.common.response.EnvelopeResponse;

import feign.FeignException;
import io.micrometer.common.util.StringUtils;
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

	@Override
	public GetMemberResponseDto getMemberInfo(String accessToken, Long memberId) {
		// 입력값 검증
		if(StringUtils.isBlank(accessToken) || memberId == null){
			throw new MemberException(MemberErrorInfo.INVALID_MEMBER_REQUEST_DATA_ERROR);
		}

		// 토큰 유효성 검증
		EnvelopeResponse<PostAuthInfoResponseDto> postAuthInfoResponseDtoEnvelopeResponse;
		try{
			postAuthInfoResponseDtoEnvelopeResponse = authApi.getMyInfo(accessToken);
		} catch (FeignException e){
			System.out.println(e.getMessage());
			throw new MemberException(MemberErrorInfo.FAIL_TO_AUTH_FEIGN_CLIENT_REQUEST);
		}
		
		// 멤버 정보
		Member member = memberRepository.findById(memberId).orElseThrow(() -> new MemberException(MemberErrorInfo.MEMBER_NOT_FOUND));

		return GetMemberResponseDto.of(member.getId(), member.getNickname(), member.getProfileImageUrl());
	}

	/**
	 * 회원 가입시 회원 정보 등록
	 * @param accessToken String
	 * @param postAuthProfileRequestDto PostAuthProfileRequestDto
	 * @param profileImage MultipartFile
	 * @see ImageApi
	 * @see JwtTokenProvider
	 * @see MemberRepository
	 */
	// @Override
	// @Transactional
	// public void saveProfile(String accessToken, PostAuthProfileRequestDto postAuthProfileRequestDto, MultipartFile profileImage) {
	// 	accessToken = parsingAccessToken(accessToken);
	// 	// 토큰 id로 멤버 찾기
	// 	Long id = jwtTokenProvider.getLoginId(accessToken);
	// 	Member member = memberRepository.findById(id)
	// 		.orElseThrow(() -> new AuthException(AuthErrorInfo.MEMBER_NOT_FOUND));
	//
	// 	// 닉네임 유효성 검사
	// 	String nickname = postAuthProfileRequestDto.getNickname();
	// 	if(StringUtils.isBlank(nickname)){
	// 		throw new AuthException(AuthErrorInfo.INVALID_AUTH_REQUEST_DATA_ERROR);
	// 	}
	// 	// 닉네임 중복 검사
	// 	Optional<Member> exist = memberRepository.findByNickname(nickname);
	// 	if(exist.isPresent() && member.getId() != exist.get().getId()){
	// 		throw new AuthException(AuthErrorInfo.EXIST_MEMBER_NICKNAME);
	// 	}
	//
	// 	String profileImageUrl = null;
	//
	// 	// 이미지 업데이트 할 때 null이 아니면 새로 들어온 이미지 저장
	// 	if(profileImage != null){
	// 		EnvelopeResponse<PostImageResponseDto> postImageResponseDtoEnvelopeResponse;
	// 		List<MultipartFile> profileImages = new ArrayList<>();
	// 		profileImages.add(profileImage);
	//
	// 		try{
	// 			postImageResponseDtoEnvelopeResponse = imageApi.getProfileImages(profileImages);
	// 		} catch (FeignException e) {
	// 			System.out.println(e.getMessage());
	// 			throw new AuthException(AuthErrorInfo.FAIL_TO_IMAGE_FEIGN_CLIENT_REQUEST);
	// 		}
	//
	// 		PostImageResponseDto postImageResponseDto = postImageResponseDtoEnvelopeResponse.getData();
	//
	// 		profileImageUrl = postImageResponseDto.getPath().get(0).getUploadFilePath();
	//
	// 	}
	// 	member.updateMember(nickname, profileImageUrl);
	// }
}
