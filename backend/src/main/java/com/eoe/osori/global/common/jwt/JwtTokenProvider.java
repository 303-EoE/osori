package com.eoe.osori.global.common.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

/**
 * JWT Token 방식을 사용할 때 필요한 기능을 정리해놓은 클래스
 * JWT Token 발행 / JWT Token Claim에서 loginId 꺼내기 / 만료기간 체크 등
 */
@Slf4j
@Component
public class JwtTokenProvider {
	@Value("${jwt.secret_key}")
	private String SECRET_KEY;

	/**
	 * SECRET_KEY 값 가져오기
	 * @param secretKey
	 * @return
	 */
	private Key getSigningKey(String secretKey) {
		byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	/**
	 * SecretKey를 사용해 Token parsing
	 * @param token
	 * @return
	 */
	public Claims extractClaims(String token) {
		return Jwts.parserBuilder()
			.setSigningKey(getSigningKey(SECRET_KEY))
			.build()
			.parseClaimsJws(token)
			.getBody();
	}

	/**
	 * JWT Token 발급
	 * @param id
	 * @param expireTimeMs
	 * @return
	 */
	private String createToken(Long id, long expireTimeMs) {
		// Claim = Jwt Token에 들어갈 정보
		Claims claims = Jwts.claims();
		claims.put("id", id); // 로그인한 멤버 Id 넣어주기
		Date issuedAt = new Date();
		Date expired = new Date(issuedAt.getTime() + expireTimeMs);
		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(issuedAt)
			.setExpiration(expired)
			.signWith(getSigningKey(SECRET_KEY), SignatureAlgorithm.HS256)
			.compact();
	}

	public String generateAccessToken(Long id) {
		return createToken(id, JwtExpirationEnum.ACCESS_TOKEN_EXPIRATION_TIME.getValue());
	}

	public String generateRefreshToken(Long id) {
		return createToken(id, JwtExpirationEnum.REFRESH_TOKEN_EXPIRATION_TIME.getValue());
	}

	/**
	 * 토큰이 만료되었는지 확인
	 * @param token
	 * @return
	 */
	public Boolean isTokenExpired(String token) {
		Date expiredDate = extractClaims(token).getExpiration();
		return expiredDate.before(new Date());
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		Long id = getLoginId(token);
		return id.toString().equals(userDetails.getUsername()) && !isTokenExpired(token);
	}

	public Long getLoginId(String token) {
		return extractClaims(token).get("id", Long.class);
	}

	/**
	 * 토큰 남은 시간 확인
	 * @param token
	 * @return
	 */
	public long getRemainMilliSeconds(String token) {
		Date expiration = extractClaims(token).getExpiration();
		Date now = new Date();
		return expiration.getTime() - now.getTime();
	}
}
