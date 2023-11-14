package com.eoe.osori.global.common.api.auth;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.eoe.osori.global.common.api.auth.dto.PostAuthInfoResponseDto;
import com.eoe.osori.global.common.response.EnvelopeResponse;

@FeignClient(name = "auth", url = "${api.auth}")
@Component
public interface AuthApi {
	@PostMapping(path = "/info")
	EnvelopeResponse<PostAuthInfoResponseDto> getMyInfo(@RequestBody String accessToken);
}
