package com.eoe.osori.domain.member.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.eoe.osori.domain.member.domain.Member;
import com.eoe.osori.domain.member.dto.GetMemberResponseDto;
import com.eoe.osori.domain.member.dto.PatchMemberRequestDto;
import com.eoe.osori.domain.member.repository.MemberRepository;
import com.eoe.osori.global.advice.error.exception.MemberException;
import com.eoe.osori.global.advice.error.info.MemberErrorInfo;
import com.eoe.osori.global.common.api.image.ImageApi;
import com.eoe.osori.global.common.api.image.dto.PostImageResponseDto;
import com.eoe.osori.global.common.response.EnvelopeResponse;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService{

	private final MemberRepository memberRepository;
	private final ImageApi imageApi;

	/**
	 * 다른 회원 정보 조회
	 * @param memberId Long
	 * @return GetMemberResponseDto
	 * @see MemberRepository
	 */
	@Override
	@Transactional
	public GetMemberResponseDto getMemberInfo(Long memberId) {
		// 입력값 검증
		if(memberId == null){
			throw new MemberException(MemberErrorInfo.INVALID_MEMBER_REQUEST_DATA_ERROR);
		}
		
		// 멤버 정보
		Member member = memberRepository.findById(memberId).orElseThrow(() -> new MemberException(MemberErrorInfo.MEMBER_NOT_FOUND));

		return GetMemberResponseDto.of(member.getId(), member.getNickname(), member.getProfileImageUrl());
	}

	/**
	 * 회원 정보 수정
	 * @param patchMemberRequestDto PatchMemberRequestDto
	 * @param profileImage MultipartFile
	 * @see ImageApi
	 * @see MemberRepository
	 */
	@Override
	@Transactional
	public void updateProfile(PatchMemberRequestDto patchMemberRequestDto, List<MultipartFile> profileImage) {

		Member member = memberRepository.findById(patchMemberRequestDto.getMemberId()).orElseThrow(() -> new MemberException(MemberErrorInfo.MEMBER_NOT_FOUND));

		// 원래 닉네임
		String nickname = member.getNickname();
		// 받은 닉네임이 null이 아니라면
		if(patchMemberRequestDto.getNickname() != null){
			// 닉네임 중복 검사
			Optional<Member> exist = memberRepository.findByNickname(patchMemberRequestDto.getNickname());
			if(exist.isPresent() && member.getId() != exist.get().getId()){
				throw new MemberException(MemberErrorInfo.EXIST_MEMBER_NICKNAME);
			}
			// 닉네임 업데이트
			nickname = patchMemberRequestDto.getNickname();
		}

		// 기존 프로필 이미지
		String profileImageUrl = member.getProfileImageUrl();
		// 프로필 이미지 새로 들어왔을 때
		if(profileImage != null || !profileImage.isEmpty()){

			EnvelopeResponse<PostImageResponseDto> postImageResponseDtoEnvelopeResponse;
			try{
				postImageResponseDtoEnvelopeResponse = imageApi.saveImages(profileImage);
			}catch(FeignException e){
				System.out.println(e.getMessage());
				throw new MemberException(MemberErrorInfo.FAIL_TO_IMAGE_FEIGN_CLIENT_REQUEST);
			}
			PostImageResponseDto postImageResponseDto = postImageResponseDtoEnvelopeResponse.getData();
			profileImageUrl = postImageResponseDto.getPath().get(0).getUploadFilePath();
		}
		// 새로 들어온 이미지가 Null일 때
		// 기존 이미지 삭제
		if(patchMemberRequestDto.isDefaultImage()){
			profileImageUrl = null;
		}
		member.updateMember(nickname, profileImageUrl);
	}
}
