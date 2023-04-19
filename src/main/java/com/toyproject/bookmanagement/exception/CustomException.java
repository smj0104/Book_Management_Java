package com.toyproject.bookmanagement.exception;

import java.util.Map;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {  //프로그램 도중 정지시 에러메시지 보냄

	private Map<String, String> errorMap;
	
	public CustomException(String message) {
		super(message); //runtimeexception에 message전달
	}
	
	public CustomException(String message, Map<String, String> errorMap) {
		super(message);
		this.errorMap = errorMap;
	}
	
	//중복확인 후에 만약에 같은 email이 존재하면
	//email 입력 input 아래에 사용중인 email입니다 메세지 출력
}
