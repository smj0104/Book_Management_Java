package com.toyproject.bookmanagement.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.toyproject.bookmanagement.dto.auth.SignupReqDto;
import com.toyproject.bookmanagement.entity.Authority;
import com.toyproject.bookmanagement.entity.User;
import com.toyproject.bookmanagement.exception.CustomException;
import com.toyproject.bookmanagement.exception.ErrorMap;
import com.toyproject.bookmanagement.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	
	private final UserRepository userRepository;
		
	public void checkDuplicatedEmail(String email)  {
		
		if(userRepository.findUserByEmail(email) != null) {  //이메일을 검색해서 가져왔을때 null이 아닐경우
//			Map<String, String> errorMap = new HashMap<>();
//			errorMap.put("email", "이미 사용중인 email입니다.");
//			
//			throw new CustomException("중복 검사 오류", errorMap);
			throw new CustomException("duplicated Email",
					ErrorMap.builder().put("email", "사용중인 이메일입니다.").build());
		}								//"email"위치에 메시지 생성하겠다
	}
	
	public void signup(SignupReqDto signupReqDto) {
		User userEntity = signupReqDto.toEntity();
		userRepository.saveUser(userEntity);
	}
	
}
