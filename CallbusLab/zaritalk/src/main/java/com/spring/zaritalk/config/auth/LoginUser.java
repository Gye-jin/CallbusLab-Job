package com.spring.zaritalk.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.spring.zaritalk.model.User;

public class LoginUser implements UserDetails{
	

	private static final long serialVersionUID = 1L;
	
	private User user;
	
    public LoginUser(User user){
        this.user = user;
    }
	
    public User getUser() {
		return user;
	}
    
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
	 Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(() -> "ROLE_" + user.getAccountType());
        return authorities;
	}

	@Override
	public String getPassword() {
		
		return user.getUserPw();
	}

	@Override
	public String getUsername() {
		return user.getAccountId();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
