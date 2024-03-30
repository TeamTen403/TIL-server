package com.teamten.til.tiler.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.teamten.til.tiler.dto.TilerJoinRequest;
import com.teamten.til.tiler.dto.TilerStatistics;
import com.teamten.til.tiler.entity.Tiler;
import com.teamten.til.tiler.exception.AppException;
import com.teamten.til.tiler.exception.ErrorCode;
import com.teamten.til.tiler.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TilerService {

	private final UserRepository userRepository;

	public List<Tiler> get() {
		return userRepository.findAll();
	}

	public String join(TilerJoinRequest dto) {

		System.out.println(dto);
		//email 중복 채크
		userRepository.findByEmail(dto.getEmail())
			.ifPresent(tiler -> {
				throw new AppException(ErrorCode.USERNAME_DUPLICATED, dto.getEmail() + "는 이미 있습니다.");
			});
		//저장
		Tiler tiler = Tiler.builder()
			.email(dto.getEmail())
			.passwd(dto.getPasswd())
			.nickName(dto.getNickName())
			.authProvider(dto.getAuthProvider())
			.build();

		System.out.println(tiler);
		userRepository.save(tiler);

		return "성공";
	}

	public TilerStatistics getStatistics(String loginInfo) {
		return null;
	}
}

