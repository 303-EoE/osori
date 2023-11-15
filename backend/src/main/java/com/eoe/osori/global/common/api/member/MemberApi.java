//package com.eoe.osori.global.common.api.member;
//
//import com.eoe.osori.global.common.api.member.dto.GetMemberDetailResponseDto;
//import com.eoe.osori.global.common.response.EnvelopeResponse;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//@FeignClient(name="member", url="${api.member}")
//public interface MemberApi {
//    @GetMapping
//    EnvelopeResponse<GetMemberDetailResponseDto> getMemberDetail(@RequestParam("member_id") Long memberId);
//}
