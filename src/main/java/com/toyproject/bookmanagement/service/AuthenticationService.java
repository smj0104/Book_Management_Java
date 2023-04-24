package com.toyproject.bookmanagement.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.toyproject.bookmanagement.dto.auth.JwtRespDto;
import com.toyproject.bookmanagement.dto.auth.LoginReqDto;
import com.toyproject.bookmanagement.dto.auth.PrincipalRespDto;
import com.toyproject.bookmanagement.dto.auth.SignupReqDto;
import com.toyproject.bookmanagement.entity.Authority;
import com.toyproject.bookmanagement.entity.User;
import com.toyproject.bookmanagement.exception.CustomException;
import com.toyproject.bookmanagement.exception.ErrorMap;
import com.toyproject.bookmanagement.repository.UserRepository;
import com.toyproject.bookmanagement.security.JwtTokenProvider;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {
	
	private final UserRepository userRepository;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final JwtTokenProvider jwtTokenProvider;

	
	public void checkDuplicatedEmail(String email) {
		if(userRepository.findUserByEmail(email) != null) {
			throw new CustomException("Duplicated Email", 
					ErrorMap.builder().put("email", "사용중인 이메일입니다").build());
		}
	}
	
	public void signup(SignupReqDto signupReqDto) {
		User userEntity = signupReqDto.toEntity();  //userID가 userEntity에 들어감
		
		userRepository.saveUser(userEntity); //한번 갔다와야 생김
		
		userRepository.saveAuthority(Authority.builder()
				.userId(userEntity.getUserId())
				.roleId(1)
				.build());
	}
	public JwtRespDto signin(LoginReqDto loginReqDto) {	//이메일 패스워드 들고옴
		
		UsernamePasswordAuthenticationToken authenticationToken =  // authentication token을 만듬
				new UsernamePasswordAuthenticationToken(loginReqDto.getEmail(), loginReqDto.getPassword()); //manager가 알아볼 수 있게 만듬
			//loadbyusername 호출함?
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken); //manager가 토크을 인증
		
		
		return jwtTokenProvider.generateToken(authentication);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User userEntity = userRepository.findUserByEmail(username);  //manager가 처리하는거라 username으로 보임 
		
		if(userEntity == null) {
			throw new CustomException("로그인 실패", ErrorMap.builder().put("email", "사용자 정보를 확인하세요").build());
		}
		
		return userEntity.toPrincipal();
	}
	
	public boolean authenticated(String accessToken) {
		return jwtTokenProvider.validateToken(jwtTokenProvider.getToken(accessToken));
	}
	
	public PrincipalRespDto getPrincipal(String accessToken) {		//로그인후 토큰 체크 bearer 자르고 확인
		Claims claims = jwtTokenProvider.getClaims(jwtTokenProvider.getToken(accessToken));
		User userEntity = userRepository.findUserByEmail(claims.getSubject());
		
		return PrincipalRespDto.builder()
				.userId(userEntity.getUserId())
				.email(userEntity.getEmail())
				.name(userEntity.getName())
				.authorities((String)claims.get("auth"))  //object라 형변환 필요
				.build();
	}

}







