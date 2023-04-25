package com.toyproject.bookmanagement.dto.book;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CategoryRespDto {
	private int categoryId;
	private String categoryName;
	
}
