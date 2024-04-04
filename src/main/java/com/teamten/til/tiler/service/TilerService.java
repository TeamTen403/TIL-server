package com.teamten.til.tiler.service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.teamten.til.challenge.entity.ChallengeParticipant;
import com.teamten.til.challenge.repository.ChallengeParticipantRepository;
import com.teamten.til.common.config.auth.token.TokenProvider;
import com.teamten.til.common.exception.InvalidException;
import com.teamten.til.common.exception.NotExistException;
import com.teamten.til.common.exception.UnauthorizedException;
import com.teamten.til.tiler.dto.TilerInfo;
import com.teamten.til.tiler.dto.TilerJoinRequest;
import com.teamten.til.tiler.dto.TilerLoginRequest;
import com.teamten.til.tiler.dto.TilerLoginResponse;
import com.teamten.til.tiler.dto.TilerStatistics;
import com.teamten.til.tiler.entity.Job;
import com.teamten.til.tiler.entity.LoginUser;
import com.teamten.til.tiler.entity.Tiler;
import com.teamten.til.tiler.exception.AppException;
import com.teamten.til.tiler.exception.ErrorCode;
import com.teamten.til.tiler.repository.TilerRepository;
import com.teamten.til.tiler.utils.CookieUtils;
import com.teamten.til.tilog.entity.Tilog;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TilerService {
	private static final String TOKEN_KEY = "til_auth";

	private final TilerRepository tilerRepository;
	private final ChallengeParticipantRepository challengeParticipantRepository;
	private final BCryptPasswordEncoder encoder;
	private final TokenProvider tokenProvider;

	@Transactional(readOnly = true)
	public TilerInfo getOne(UUID tilerId) {
		return tilerRepository.findById(tilerId)
			.map(TilerInfo::from)
			.orElseThrow(() -> new NotExistException());
	}

	@Transactional(readOnly = true)
	public TilerLoginResponse login(HttpServletResponse response, TilerLoginRequest request) {
		//email없음
		Tiler selectTiler = tilerRepository.findByEmail(request.getEmail())
			.orElseThrow(() -> new NotExistException());

		//password 틀림
		if (!encoder.matches(request.getPasswd(), selectTiler.getPasswd())) {
			throw new InvalidException();
		}

		String token = tokenProvider.createToken(selectTiler);

		CookieUtils.addCookie(response, TOKEN_KEY, token);

		return TilerLoginResponse.builder().token(token).build();
	}

	@Transactional
	public void join(TilerJoinRequest request) {
		//email 중복 채크
		tilerRepository.findByEmail(request.getEmail())
			.ifPresent(tiler -> {
				throw new AppException(ErrorCode.EMAIL_DUPLICATED, request.getEmail() + "는 이미 있습니다.");
			});

		//회원가입 성공
		Tiler tiler = Tiler.builder()
			.email(request.getEmail())
			.passwd(encoder.encode(request.getPasswd()))
			.nickname(request.getNickname())
			.job(Job.builder().id(request.getJobId()).build())
			.profileImage(request.getProfileImage())
			.authProvider(request.getAuthProvider())
			.build();

		tilerRepository.save(tiler);
	}

	@Transactional(readOnly = true)
	public TilerStatistics getStatistics(LoginUser loginUser) {
		Tiler tiler = tilerRepository.findById(loginUser.getId()).orElseThrow(() -> new UnauthorizedException());

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

