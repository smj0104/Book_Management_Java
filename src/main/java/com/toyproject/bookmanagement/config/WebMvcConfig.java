package com.toyproject.bookmanagement.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {//테스트할때만 사용

	@Override
	public void addCorsMappings(CorsRegistry registry) { //3000번 포트에서 오는 모든 요청 허락
		registry.addMapping("/**")
			.allowedMethods("*");
			//.allowedOrigins("http://localhost:3000");
	}
}
