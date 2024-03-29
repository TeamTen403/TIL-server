package com.teamten.til.tiler.service;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	private final BCryptPasswordEncoder encoder;

	public List<Tiler> get() {
		return userRepository.findAll();
	}

	public String login(String email, String passwd){
		//email없음
		Tiler selectTiler = userRepository.findByEmail(email)
				.orElseThrow(()->new AppException(ErrorCode.EMAIL_NOTFOUND, email + "이 없습니다."));

		System.out.println(encoder.matches(selectTiler.getPasswd(), passwd));
		//password 틀림
		if(!encoder.matches(selectTiler.getPasswd(), passwd)){
			throw new AppException(ErrorCode.INVALiD_PASSWORd,"패스워드를 잘못입력하셨습니다.");
		}

		//

		return "";
	}

	public String join(TilerJoinRequest dto) {
//		System.out.println(dto);
		//email 중복 채크
		userRepository.findByEmail(dto.getEmail())
			.ifPresent(tiler -> {
				throw new AppException(ErrorCode.EMAIL_DUPLICATED, dto.getEmail() + "는 이미 있습니다.");
			});
		//회원가입 성공
		Tiler tiler = Tiler.builder()
			.email(dto.getEmail())
			.passwd(encoder.encode(dto.getPasswd()))
			.nickName(dto.getNickName())
			.job(dto.getJob())
			.authProvider(dto.getAuthProvider())
			.build();
		userRepository.save(tiler);
		return "성공";
	}

	public TilerStatistics getStatistics(String loginInfo) {
		return null;
	}
}

