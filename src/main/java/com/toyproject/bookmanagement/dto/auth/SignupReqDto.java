package com.toyproject.bookmanagement.dto.auth;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.toyproject.bookmanagement.entity.User;

import lombok.Data;

@Data
public class SignupReqDto {
	
	@Email
	private String email;
	
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,16}$",
			message = "비밀번호는 영문자, 숫자, 특수문자를 포함하여 8 ~ 16자로 작성하세요.")	//^정규식 시작 $종료 ?=앞쪽이 일치하는지 확인  .*모든글자가 여러개 있거나없거나
	private String password;	// *\\ 숫자를 하나 포함해야한다(이 중 하나라도 포함하지않을경우 false)  [A-Za-z\\d@$!%*#?&]포함할수있는 글자  {8,} = 글자갯수(최소값,최대값)
	
	@Pattern(regexp = "^[가-힇]{2,7}$",
			message = "한글이름만 작성 가능합니다.")
	private String name;
	
	public User toEntity() {
		return User.builder()
				.email(email)
				.password(new BCryptPasswordEncoder().encode(password))//dto에서 entity로 넘길때 암호화가 필요
				.name(name)
				.build();
	}
}
