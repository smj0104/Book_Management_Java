package com.toyproject.bookmanagement.repository;

import org.apache.ibatis.annotations.Mapper;

import com.toyproject.bookmanagement.entity.User;

@Mapper
public interface UserRepository {

	public User findUserByEmail(String email); //void를 안바꿔주면 return이 없음
	public int saveUser(User user);  //insert는 무조건 int dto가 아닌 userentity가 들어가야함
	
}
