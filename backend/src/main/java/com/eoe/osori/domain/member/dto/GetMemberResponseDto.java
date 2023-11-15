package com.eoe.osori.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class GetMemberResponseDto {
	private Long id;
	private String nickname;
	private String profileImageUrl;

	public static GetMemberResponseDto of(Long id, String nickname, String profileImageUrl){
		return GetMemberResponseDto.builder()
			.id(id)
			.nickname(nickname)
			.profileImageUrl(profileImageUrl)
			.build();
	}
}
