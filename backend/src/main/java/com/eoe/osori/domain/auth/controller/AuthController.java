package com.eoe.osori.domain.auth.controller;

import com.eoe.osori.domain.auth.dto.*;
import com.eoe.osori.domain.auth.service.AuthService;
import com.eoe.osori.domain.auth.service.redis.TokenService;
import com.eoe.osori.global.common.response.EnvelopeResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final TokenService tokenService;


    /**
     * 로그인 / 회원가입
     *
     * @param postAuthLoginRequestDto PostAuthRequestDto
     * @return ResponseEntity<EnvelopeResponse < PostAuthResponseDto>>
     * @see AuthService
     */
    @PostMapping("/login")
    public ResponseEntity<EnvelopeResponse<PostAuthLoginResponseDto>> login(@RequestBody PostAuthLoginRequestDto postAuthLoginRequestDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(EnvelopeResponse.<PostAuthLoginResponseDto>builder()
                        .code(HttpStatus.OK.value())
                        .data(authService.login(postAuthLoginRequestDto))
                        .build());
    }

    /**
     * 토큰으로 회원 정보 조회
     *
     * @param accessToken String
     * @return ResponseEntity<EnvelopeResponse<PostAuthInfoResponseDto>>
     * @see AuthService
     */
    @GetMapping("/info")
    public ResponseEntity<EnvelopeResponse<PostAuthInfoResponseDto>> info(@RequestHeader("Authorization") String accessToken) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(EnvelopeResponse.<PostAuthInfoResponseDto>builder()
                        .code(HttpStatus.OK.value())
                        .data(authService.getLoginUserInfo(accessToken))
                        .build());
    }

    /**
     * 회원 가입 시 회원 정보 등록
     *
     * @param postAuthProfileRequestDto PostAuthProfileRequestDto
     * @param profileImage              MultipartFile
     * @return ResponseEntity<EnvelopeResponse<PostAuthProfileResponseDto>>
     * @see AuthService
     */
    @PostMapping("/profile")
    public ResponseEntity<EnvelopeResponse<PostAuthProfileResponseDto>> profile(
            @RequestPart(value = "postAuthProfileRequestDto") PostAuthProfileRequestDto postAuthProfileRequestDto,
            @RequestPart(value = "profileImage", required = false) MultipartFile profileImage) {
        authService.saveProfile(postAuthProfileRequestDto, profileImage);
        return ResponseEntity.status(HttpStatus.OK)
                .body(EnvelopeResponse.<PostAuthProfileResponseDto>builder()
                        .code(HttpStatus.OK.value())
                        .data(authService.saveProfile(postAuthProfileRequestDto, profileImage))
                        .build());
    }

    /**
     * 로그아웃 시 리프레시 토큰 삭제
     *
     * @param accessToken String
     * @return ResponseEntity<EnvelopeResponse<Void>>
     * @see TokenService
     */
    @GetMapping("/token/logout")
    public ResponseEntity<EnvelopeResponse<Void>> logout(@RequestHeader("Authorization") String accessToken) {

        // 엑세스 토큰으로 현재 Redis 정보 삭제
        tokenService.removeRefreshToken(accessToken);
        return ResponseEntity.status(HttpStatus.OK)
                .body(EnvelopeResponse.<Void>builder()
                        .code(HttpStatus.OK.value())
                        .build());
    }

    /**
     * refresh 토큰으로 access 토큰 재발급
     *
     * @param refreshToken String
     * @return ResponseEntity<EnvelopeResponse<PostAuthReissueTokenResponseDto>>
     * @see TokenService
     */
    @GetMapping("/token/refresh")
    public ResponseEntity<EnvelopeResponse<PostAuthReissueTokenResponseDto>> refresh(@RequestHeader("Authorization") String refreshToken) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(EnvelopeResponse.<PostAuthReissueTokenResponseDto>builder()
                        .code(HttpStatus.OK.value())
                        .data(tokenService.reissueAccessToken(refreshToken))
                        .build());
    }
}