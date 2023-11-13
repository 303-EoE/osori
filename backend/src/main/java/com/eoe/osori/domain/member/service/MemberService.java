package com.eoe.osori.domain.member.service;

import com.eoe.osori.domain.member.dto.GetMemberMyPageResponseDto;

public interface MemberService {

	GetMemberMyPageResponseDto getMyInfo(String accessToken);
}
