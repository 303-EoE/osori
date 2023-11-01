package com.eoe.osori.global.common.api.kakao.service;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "auth.kakao")
public class KakaoConstantProvider {
	private final String clientId;
	private final String addressConvertUri;
}
