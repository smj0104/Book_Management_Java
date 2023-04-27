package com.toyproject.bookmanagement.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.toyproject.bookmanagement.entity.Book;
import com.toyproject.bookmanagement.entity.Category;
import com.toyproject.bookmanagement.entity.RentalList;

@Mapper
public interface BookRepository {		//xml에 namespace에 경로 잡아주기
	public Book getBook(int bookId);
	public List<Book> searchBooks(Map<String, Object> map);		//뒤에 문자나 숫자가 올수있기에 object 
	public int getTotalCount(Map<String, Object> map);
	public List<Category> getCategories();		//다 들고올거라 파라미터X
	public int getLikeCount(int bookId);
	public int getLikeStatus(Map<String, Object> map);
	public int setLike(Map<String, Object> map);
	public int disLike(Map<String, Object> map);
	public List<RentalList> getRentalListByBookId(int bookId);
	public int rentalBooks(Map<String, Object> map);
	public int returnBooks(Map<String, Object> map);
}
