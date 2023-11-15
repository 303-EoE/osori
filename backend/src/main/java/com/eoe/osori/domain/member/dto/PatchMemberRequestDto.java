package com.eoe.osori.domain.member.dto;

import lombok.Getter;

@Getter
public class PatchMemberRequestDto {
	private Long memberId;
	private String nickname;
	private boolean defaultImage;
}
