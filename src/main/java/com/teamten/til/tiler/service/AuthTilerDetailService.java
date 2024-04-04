package com.teamten.til.tiler.service;

import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.teamten.til.common.exception.NotExistException;
import com.teamten.til.tiler.entity.LoginUser;
import com.teamten.til.tiler.entity.Tiler;
import com.teamten.til.tiler.repository.TilerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthTilerDetailService implements UserDetailsService {

	private final TilerRepository tilerRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String email)
		throws UsernameNotFoundException {
		Tiler user = tilerRepository.findByEmail(email)
			.orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 email : " + email));

		return LoginUser.create(user);
	}

	@Transactional
	public UserDetails loadUserById(UUID id) {
		Tiler user = tilerRepository.findById(id).orElseThrow(
			() -> new NotExistException("존재하지 않는 id : " + id));

		return LoginUser.create(user);
	}
}
