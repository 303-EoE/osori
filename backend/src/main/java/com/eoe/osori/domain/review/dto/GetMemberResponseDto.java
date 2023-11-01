package com.eoe.osori.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class GetMemberResponseDto {

	private Long memberId;
	private String memberNickname;
}
