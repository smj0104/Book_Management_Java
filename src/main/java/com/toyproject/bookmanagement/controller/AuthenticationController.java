package com.toyproject.bookmanagement.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.toyproject.bookmanagement.aop.annotation.ValidAspect;
import com.toyproject.bookmanagement.dto.auth.SignupReqDto;
import com.toyproject.bookmanagement.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor	//생성자 생성
@RequestMapping("/auth")  // 이 컨트롤러 앞의 요청들은 전부 auth붙음
public class AuthenticationController {

	private final AuthenticationService authenticationService;
	
	@PostMapping("/login")
	public ResponseEntity<?> login() {
		return ResponseEntity.ok(null);
		
	}
	
	@CrossOrigin
	@ValidAspect	//aop가 먼저 동작함
	@PostMapping("/signup")	//@valid가 검사 /오류가 있을시 bindingresult에 들어감
	public ResponseEntity<?> signup(@Valid @RequestBody SignupReqDto signreqDto, BindingResult bindingResult) {
		authenticationService.checkDuplicatedEmail(signreqDto.getEmail());
		authenticationService.signup(signreqDto);  //권한주기는 집에서
		return ResponseEntity.ok(null);
	}
}
