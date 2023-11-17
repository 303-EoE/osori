package com.eoe.osori.domain.member.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.eoe.osori.domain.member.dto.GetMemberMyPageResponseDto;
import com.eoe.osori.domain.member.dto.GetMemberResponseDto;
import com.eoe.osori.domain.member.dto.PatchMemberRequestDto;

public interface MemberService {
	GetMemberResponseDto getMemberInfo(Long memberId);
	void updateProfile(PatchMemberRequestDto patchMemberRequestDto, List<MultipartFile> profileImage);
}
