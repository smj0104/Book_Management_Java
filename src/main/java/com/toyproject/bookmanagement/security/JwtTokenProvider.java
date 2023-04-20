package com.toyproject.bookmanagement.security;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.toyproject.bookmanagement.dto.auth.JwtRespDto;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

	private final Key key;
	
	public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
		key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
	}
	public JwtRespDto generateToken(Authentication authentication) {
		
		StringBuilder builder = new StringBuilder();
		
		String authorities = builder.toString();
		
		authentication.getAuthorities().forEach(authority -> {
			builder.append(authority.getAuthority() + ",");
		});
		builder.delete(builder.length() - 1, builder.length()); //ROLE_USER, 총 length는 10 index는 9 인덱스 시작번호 맞추려면 - 1
		
		Date tokenExpiresDate = new Date(new Date().getTime() + (1000 * 60 * 60 * 24));
		
		String accessToken = Jwts.builder()  //jwt생성 내용은 없음
				.setSubject(authentication.getName()) 			//토큰의 제목 (email을 여기 넣음)
				.claim("auth", authorities)				//auth
				.setExpiration(tokenExpiresDate) 		//토큰의 만료기간
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();
		
		return JwtRespDto.builder().grantType("Bearer").accessToken(accessToken).build();
	}
	
}
