package com.toyproject.bookmanagement.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.toyproject.bookmanagement.entity.Book;
import com.toyproject.bookmanagement.entity.Category;

@Mapper
public interface BookRepository {		//xml에 namespace에 경로 잡아주기
	public List<Book> searchBooks(Map<String, Object> map);		//뒤에 문자나 숫자가 올수있기에 object 
	public int getTotalCount(Map<String, Object> map);
	public List<Category> getCategoies();		//다 들고올거라 파라미터X
	
	
}
