package com.eoe.osori.global.common.api.member;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "member",url = "${api.member}")
public interface MemberApi {
}
