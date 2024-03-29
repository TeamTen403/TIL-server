package com.teamten.til.tiler.service;

import com.teamten.til.tiler.dto.TilerJoinRequest;
import com.teamten.til.tiler.entity.AuthProvider;
import com.teamten.til.tiler.entity.Tiler;
import com.teamten.til.tiler.exception.AppException;
import com.teamten.til.tiler.exception.ErrorCode;
import com.teamten.til.tiler.repository.UserRepository;
import org.springframework.stereotype.Service;

import com.teamten.til.tiler.dto.TilerStatistics;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TilerService {

	private final UserRepository userRepository;
	public String join(TilerJoinRequest dto){
		//email 중복 채크
		userRepository.findByEmail(dto.getEmail())
				.ifPresent(tiler -> {
					throw new AppException(ErrorCode.USERNAME_DUPLICATED,  dto.getEmail() + "는 이미 있습니다.");
				});
		//저장
		Tiler tiler = Tiler.builder()
				.email(dto.getEmail())
				.passwd(dto.getPasswd())
				.nickName(dto.getNickName())

				.authProvider(dto.getAuthProvider())
				.build();
		userRepository.save(tiler);

		return "성공";
	}
	public TilerStatistics getStatistics(String loginInfo) {
		return null;
	}
}

