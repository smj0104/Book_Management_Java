package com.toyproject.bookmanagement.aop;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import com.toyproject.bookmanagement.exception.CustomException;

@Aspect
@Component
public class ValidationAop {
		
	@Pointcut("@annotation(com.toyproject.bookmanagement.aop.annotation.ValidAspect)")
	private void pointCut() {}
		
	@Around("pointCut()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
			
		Object[] args = joinPoint.getArgs();
		BindingResult bindingResult = null;
		
		for(Object arg : args) {  
			if(arg.getClass() == BeanPropertyBindingResult.class) { 
				bindingResult = (BeanPropertyBindingResult) arg;
			}
		}
		
		if(bindingResult.hasErrors()) {  //우선 에러가 있는지 파악
			 Map<String, String> errorMap = new HashMap<>();  //key와 value로 띄울 에러메시지를 만든다
			 
			bindingResult.getFieldErrors().forEach(error -> {
				errorMap.put(error.getField(), error.getDefaultMessage());
			});
			
			throw new CustomException("Validation Failed", errorMap);  //만들어진 주소가 advicecontroller의 cutomexception e에 들어감
			
		}
			
		return joinPoint.proceed();	
	}
 }
