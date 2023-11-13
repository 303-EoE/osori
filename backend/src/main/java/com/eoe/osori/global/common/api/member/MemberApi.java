package com.eoe.osori.global.common.api.member;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.eoe.osori.global.common.api.member.dto.GetMemberResponseDto;
import com.eoe.osori.global.common.response.EnvelopeResponse;

@FeignClient(name = "member",url = "${api.member}")
public interface MemberApi {

	@GetMapping()
	EnvelopeResponse<GetMemberResponseDto> getOtherMember(@RequestParam("member_id") Long memberId);

	@GetMapping("/my-page")
	EnvelopeResponse<GetMemberResponseDto> getMember();

}
