package com.teamten.til.tiler.service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.teamten.til.challenge.entity.ChallengeParticipant;
import com.teamten.til.challenge.repository.ChallengeParticipantRepository;
import com.teamten.til.common.exception.UnauthorizedException;
import com.teamten.til.tiler.dto.TilerJoinRequest;
import com.teamten.til.tiler.dto.TilerStatistics;
import com.teamten.til.tiler.entity.Job;
import com.teamten.til.tiler.entity.Tiler;
import com.teamten.til.tiler.exception.AppException;
import com.teamten.til.tiler.exception.ErrorCode;
import com.teamten.til.tiler.repository.UserRepository;
import com.teamten.til.tiler.utils.JwtTokenUtil;
import com.teamten.til.tilog.entity.Tilog;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TilerService {

	private final UserRepository userRepository;
	private final ChallengeParticipantRepository challengeParticipantRepository;
	private final BCryptPasswordEncoder encoder;
	@Value("${jwt.token.secret}")
	private String key;
	private Long expireTimeMs = 10000 * 60 * 60L;

	public List<Tiler> get() {
		return userRepository.findAll();
	}

	public Tiler getOne(UUID tilerId) {
		return userRepository.findById(tilerId).orElse(null);
	}

	public String login(String email, String passwd) {
		//email없음
		Tiler selectTiler = userRepository.findByEmail(email)
			.orElseThrow(() -> new AppException(ErrorCode.EMAIL_NOTFOUND, email + "이 없습니다."));

		System.out.println(encoder.matches(selectTiler.getPasswd(), passwd));

		//password 틀림
		if (!encoder.matches(passwd, selectTiler.getPasswd())) {
			throw new AppException(ErrorCode.INVALiD_PASSWORd, "패스워드를 잘못입력하셨습니다.");
		}

		//
		String token = JwtTokenUtil.createToken(selectTiler.getEmail(), key, expireTimeMs);
		return token;
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
			.job(Job.builder().id(dto.getJobId()).build())
			.authProvider(dto.getAuthProvider())
			.build();
		userRepository.save(tiler);
		return "성공";
	}

	@Transactional(readOnly = true)
	public TilerStatistics getStatistics(String tilerId) {
		UUID id = UUID.fromString(tilerId); // TODO: 수정필요

		Tiler tiler = userRepository.findById(id).orElseThrow(() -> new UnauthorizedException());

		List<Tilog> tilogList = tiler.getTilogList().stream()
			.sorted(Comparator.comparing(Tilog::getRegYmdt).reversed())
			.collect(Collectors.toList());

		int consecutiveDays = getConsecutiveDays(tilogList);

		// 연속 몇일했는지
		int totalTilog = tilogList.size();

		long totalLikes = tilogList.stream()
			.mapToLong(Tilog::getLikes)
			.sum();

		int totalChallenge = challengeParticipantRepository.findAllByTilerAndIsSuccessTrue(tiler)
			.stream()
			.mapToInt(ChallengeParticipant::getScore)
			.sum();

		return TilerStatistics.builder()
			.consecutiveDays(consecutiveDays)
			.totalChallenge(totalChallenge)
			.totalLikes((int)totalLikes)
			.totalTilog(totalTilog)
			.build();
	}

	private int getConsecutiveDays(List<Tilog> tilogList) {
		int consecutiveDays = 0;

		if (tilogList.size() == 0) {
			return 0;
		}

		LocalDate today = LocalDate.now();
		LocalDate yesterday = today.minusDays(1);

		LocalDate current = tilogList.get(0).getRegYmdt().toLocalDate();

		// 가장최근이 2일 이상 지난 경우
		if (current.isBefore(yesterday)) {
			return 0;
		}

		LocalDate lastRegYmdt = null;

		for (Tilog tilog : tilogList) {
			LocalDate regYmdt = tilog.getRegYmdt().toLocalDate();

			if (lastRegYmdt == null || regYmdt.isEqual(lastRegYmdt.minusDays(1))) {
				consecutiveDays++;
			} else {
				break;
			}

			lastRegYmdt = regYmdt;
		}

		return consecutiveDays;
	}
}

