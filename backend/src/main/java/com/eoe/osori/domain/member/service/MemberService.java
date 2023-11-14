package com.eoe.osori.domain.member.service;

import com.eoe.osori.domain.member.dto.GetMemberMyPageResponseDto;
import com.eoe.osori.domain.member.dto.GetMemberResponseDto;

public interface MemberService {
	GetMemberMyPageResponseDto getMyInfo(String accessToken);
	GetMemberResponseDto getMemberInfo(String accessToken, Long memberId);
}
