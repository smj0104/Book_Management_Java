package com.toyproject.bookmanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public BCryptPasswordEncoder passwordEncoder() { 
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {  //void라 리턴x http말고 쓸거없음
		http.csrf().disable();  //반드시 막아둘것(요청 안날아감)
		http.httpBasic().disable();
		http.formLogin().disable();
		
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);  //세션 사용 안함
		
		http.authorizeRequests()
			.antMatchers("/auth/**")
			.permitAll()	//앞에 auth가 붙으면 전부 허용
			.anyRequest()
			.authenticated();
		
	}
}
