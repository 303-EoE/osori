package com.eoe.osori.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class GetMemberMyPageResponseDto {

	private Long id;
	private String nickname;
	private String profileImageUrl;

	public static GetMemberMyPageResponseDto of(Long id, String nickname, String profileImageUrl){
		return GetMemberMyPageResponseDto.builder()
			.id(id)
			.nickname(nickname)
			.profileImageUrl(profileImageUrl)
			.build();
	}
}
