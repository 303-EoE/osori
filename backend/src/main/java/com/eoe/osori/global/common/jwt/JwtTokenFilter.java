package com.eoe.osori.global.common.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.eoe.osori.global.advice.error.exception.AuthException;
import com.eoe.osori.global.advice.error.info.AuthErrorInfo;
import com.eoe.osori.global.common.security.UserDetailsServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 사용자 요청에서 JWT Token을 추출하고
 * 통과하면 권한 부여
 * 실패하면 권한 부여 없이 다음 필터로 진행
 */
@RequiredArgsConstructor
@Component
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

	@Value("${jwt.secret_key}")
	private String SECRET_KEY;
	private final JwtTokenProvider jwtTokenProvider;
	private final UserDetailsServiceImpl userDetailsService;

	/**
	 * refresh token을 이용한 access token 재발급시 필터를 거치지 않도록 합니다.
	 */
	@Override
	protected boolean shouldNotFilter(HttpServletRequest req) throws ServletException {
		return req.getRequestURI().contains("/reissue-token");
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		// 로그아웃 체크 기능 구현3
		// 로그아웃 상태면 엑세스 토큰 만료 전이라도 유효하지 않음

		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

		// Header의 Authorization 값이 비어있으면 Jwt Token을 전송하지 않음
		if(authorizationHeader == null){
			filterChain.doFilter(request, response);
			return;
		}

		// Header의 Authorization 값이 'Bearer '로 시작하지 않으면 잘못된 토큰
		if(!authorizationHeader.startsWith("Bearer ")){
			filterChain.doFilter(request, response);
			return;
		}

		// 전송받은 값에서 'Bearer ' 뒷부분(JWT Token) 추출
		String token = authorizationHeader.split(" ")[1];

		// 전송받은 JWT Token이 만료되었으면 다음 필터 진행 (인증X)
		if(jwtTokenProvider.isTokenExpired(token)){
			filterChain.doFilter(request, response);
			return;
		}

		// Jwt Token에서 id 추출
		Long id = jwtTokenProvider.getLoginId(token);

		// 추출한 id로 member 찾아오기
		UserDetails userDetails = userDetailsService.loadUserByUsername(id.toString());

		// 엑세스 토큰 생성 시 사용된 id와 현재 id가 일치하는지 확인
		equalsUsernameFromTokenAndUserDetails(userDetails.getUsername(), id.toString());

		// 엑세스 토큰의 유효성 검증
		validateAccessToken(token, userDetails);

		// securityContextHolder에 인증된 회원 정보 저장
		processSecurity(request, userDetails);

		// 다음 순서 필터로 넘어가기
		filterChain.doFilter(request, response);

	}

	/**
	 * 토큰 관련 에러처리
	 */

	private void equalsUsernameFromTokenAndUserDetails(String userDetailsUsername, String tokenUsername){
		if(!userDetailsUsername.equals(tokenUsername)){
			throw new AuthException(AuthErrorInfo.MISMATCH_TOKEN_ID);
		}
	}

	private void validateAccessToken(String accessToken, UserDetails userDetails){
		if(!jwtTokenProvider.validateToken(accessToken, userDetails)){
			throw new AuthException(AuthErrorInfo.INVALID_TOKEN);
		}
	}

	private void processSecurity(HttpServletRequest request, UserDetails userDetails){
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
	}

	// private String getAccessToken(HttpServletRequest request){
	// 	String headerAuth = request.getHeader("accessToken");
	// 	log.info("[header] {}", headerAuth);
	// 	if(StringUtils.hasText(headerAuth) && headerAuth.startsWith(JwtHeaderUtilEnum.GRANT_TYPE.getValue())){
	// 		return headerAuth.substring(JwtHeaderUtilEnum.GRANT_TYPE.getValue().length());
	// 	}
	// 	return null;
	// }
}
