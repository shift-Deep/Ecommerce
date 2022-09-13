package com.sheryians.major.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetail extends User implements UserDetails {
	
	public CustomUserDetail(User user) {
		super(user);
	}
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TO DO Auto-generated method stub
		List<GrantedAuthority> authorityList = new ArrayList<>();
		super.getRoles().forEach(role -> {
			authorityList.add(new SimpleGrantedAuthority(role.getName()));
			});
		return authorityList;
	}

	@Override
	public String getUsername() {
		// TO DO Auto-generated method stub
		return super.getEmail();
	}
	
	@Override
	public String getPassword() {
		// TO DO Auto-generated method stub
		return super.getPassword();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TO DO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TO DO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TO DO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TO DO Auto-generated method stub
		return true;
	}

}
