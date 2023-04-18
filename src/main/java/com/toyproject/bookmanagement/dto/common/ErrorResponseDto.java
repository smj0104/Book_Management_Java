package com.toyproject.bookmanagement.dto.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseDto<T> {
	private String messsage;
	private T errorData;
}
