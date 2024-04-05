package com.teamten.til.tiler.entity;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;

@Getter
public class LoginUser implements UserDetails {

	private UUID id;
	private String email;
	private Tiler user;
	private Collection<? extends GrantedAuthority> authorities;

	public LoginUser(UUID id, String email, Tiler user, Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.email = email;
		this.user = user;
		this.authorities = authorities;
	}

	public static LoginUser create(Tiler user) {
		List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

		return new LoginUser(
			user.getId(),
			user.getEmail(),
			user,
			authorities);
	}
	
	@Override
	public String getUsername() {
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

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return null;
	}
}