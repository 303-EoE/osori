package com.eoe.osori.global.common.jwt;

import com.eoe.osori.global.advice.error.exception.AuthException;
import com.eoe.osori.global.advice.error.info.AuthErrorInfo;
import com.eoe.osori.global.common.security.UserDetailsServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

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
     * refresh token을 이용한 access token 재발급시 필터를 거치지 않게 하기위해 설정
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest req) throws ServletException {
        return req.getRequestURI().contains("/token");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        String accessToken = getAccessToken(request);
        if (accessToken != null && !accessToken.equals("undefined")) {
            Long id = null;
            try {
                id = jwtTokenProvider.getLoginId(accessToken);
            } catch (ExpiredJwtException e) {
                throw new AuthException(AuthErrorInfo.EXPIRED_ACCESS_TOKEN);
            } catch (SignatureException e) {
                throw new AuthException(AuthErrorInfo.INVALID_TOKEN);
            }
            if (id != null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(id.toString());
                // 엑세스 토큰 생성 시 사용된 아이디와 현재 아이디가 일치하는지 확인
                equalsUsernameFromTokenAndUserDetails(userDetails.getUsername(), id.toString());
                // 엑세스 토큰의 유효성 검증
                validateToken(accessToken, userDetails);
                // securityContextHolder에 인증된 회원 정보 저장
                processSecurity(request, userDetails);
            }
        }
        // 다음 순서 필터로 넘어가기
        try {
            filterChain.doFilter(request, response);
        } catch (IOException e) {
            throw new AuthException(AuthErrorInfo.IOEXCEPTION_ERROR);
        } catch (ServletException e) {
            throw new AuthException(AuthErrorInfo.SERVLET_EXCEPTION_ERROR);
        }
    }

    /**
     * 토큰 관련 에러처리
     */

    private void equalsUsernameFromTokenAndUserDetails(String userDetailsUsername, String tokenUsername) {
        if (!userDetailsUsername.equals(tokenUsername)) {
            throw new AuthException(AuthErrorInfo.MISMATCH_TOKEN_ID);
        }
    }

    private void validateToken(String token, UserDetails userDetails) {
        if (!jwtTokenProvider.validateToken(token, userDetails)) {
            throw new AuthException(AuthErrorInfo.INVALID_TOKEN);
        }
    }

    private void processSecurity(HttpServletRequest request, UserDetails userDetails) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }

    private String getAccessToken(HttpServletRequest request) {
        String headerAuth = request.getHeader(JwtHeaderUtilEnum.AUTHORIZATION.getValue());
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(JwtHeaderUtilEnum.GRANT_TYPE.getValue())) {
            return headerAuth.substring(JwtHeaderUtilEnum.GRANT_TYPE.getValue().length());
        }
        return null;
    }

    private void setResponse(HttpServletResponse response, HttpStatus httpStatus, String errorMessage) throws RuntimeException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(httpStatus.value());
        response.getWriter().print(errorMessage);
    }
}
