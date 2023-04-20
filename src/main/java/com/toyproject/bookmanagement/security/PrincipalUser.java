package com.toyproject.bookmanagement.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.toyproject.bookmanagement.entity.Authority;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PrincipalUser implements UserDetails {
	private int userId;
	private String email;
	private String password;
	private List<Authority> authorities;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		
		this.authorities.forEach(authority -> {
			authorities.add(new SimpleGrantedAuthority(authority.getRole().getRoleName()));
		});
		
		return authorities;  //SimpleGrantedAuthority를 담을 리스트를 리턴
	}

	@Override
	public String getPassword() { 
		return password;	//암호화된 비밀번호
	}

	@Override
	public String getUsername() {//security가 사용
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
