package com.teamten.til.tiler.entity;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.Getter;

@Getter
public class LoginUser implements OAuth2User, UserDetails {

	private UUID id;
	private String email;
	private Tiler user;
	private Collection<? extends GrantedAuthority> authorities;
	private Map<String, Object> attributes;

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
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	@Override
	public String getName() {
		return String.valueOf(user.getNickname());
	}

	@Override
	public String getPassword() {
		return null;
	}
}