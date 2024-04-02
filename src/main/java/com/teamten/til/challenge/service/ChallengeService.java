package com.teamten.til.challenge.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.teamten.til.challenge.dto.ChallengeInfo;
import com.teamten.til.challenge.dto.ChallengeInfoResponse;
import com.teamten.til.challenge.entity.Challenge;
import com.teamten.til.challenge.entity.ChallengeParticipant;
import com.teamten.til.challenge.entity.MissionType;
import com.teamten.til.challenge.repository.ChallengeParticipantRepository;
import com.teamten.til.challenge.repository.ChallengeRepository;
import com.teamten.til.common.exception.DuplicatedException;
import com.teamten.til.common.exception.NotExistException;
import com.teamten.til.tiler.entity.Tiler;
import com.teamten.til.tilog.entity.Tilog;
import com.teamten.til.tilog.repository.TilogRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChallengeService {
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
	private final ChallengeRepository challengeRepository;
	private final ChallengeParticipantRepository participantRepository;
	private final TilogRepository tilogRepository;

	public ChallengeInfo applyChallenge(Long challengeId, String tilerId) {
		Tiler tiler = Tiler.createById(tilerId);

		Challenge challenge = challengeRepository.findById(challengeId)
			.filter(Challenge::inProgress)
			.orElseThrow(() -> new NotExistException());

		participantRepository.findByChallengeAndTiler(challenge, tiler).ifPresent(challengeParticipant -> {
			throw new DuplicatedException();
		});

		ChallengeParticipant challengeParticipant = ChallengeParticipant.builder()
			.challenge(challenge)
			.tiler(tiler)
			.build();

		participantRepository.save(challengeParticipant);

		int myAmount;

		String start = challenge.getStartYmd().format(FORMATTER);
		String end = challenge.getEndYmd().format(FORMATTER);

		List<Tilog> tilogList = tilogRepository.findAllByTilerAndRegYmdGreaterThanEqualAndRegYmdLessThanEqualOrderByRegYmdAsc(
			tiler, start, end);

		if (challenge.getMissionType() == MissionType.ACCUMULATE) {
			myAmount = tilogList.size();
		} else {
			myAmount = getMaxConsecutiveDays(tilogList);
		}

		return ChallengeInfo.of(challenge, true, myAmount);
	}

	public ChallengeInfoResponse getChallengeList(String tilerId) {
		Tiler tiler = Tiler.createById(tilerId);

		List<ChallengeInfo> challengeInfos = challengeRepository.findAll().stream().map(challenge -> {
			boolean isParticipant = participantRepository.findByChallengeAndTiler(challenge, tiler).isPresent();

			int myAmount;

			String start = challenge.getStartYmd().format(FORMATTER);
			String end = challenge.getEndYmd().format(FORMATTER);

			List<Tilog> tilogList = tilogRepository.findAllByTilerAndRegYmdGreaterThanEqualAndRegYmdLessThanEqualOrderByRegYmdAsc(
				tiler, start, end);

			if (challenge.getMissionType() == MissionType.ACCUMULATE) {
				myAmount = tilogList.size();
			} else {
				myAmount = getMaxConsecutiveDays(tilogList);
			}

			return ChallengeInfo.of(challenge, isParticipant, myAmount);
		}).collect(Collectors.toList());

		return ChallengeInfoResponse
			.builder()
			.challengeInfos(challengeInfos)
			.build();
	}

	private int getMaxConsecutiveDays(List<Tilog> tilogList) {
		if (tilogList.size() == 0) {
			return 0;
		}

		int consecutiveDays = 1;
		int maxConsecutiveDays = 1;

		for (int i = 1; i < tilogList.size(); i++) {
			LocalDate currDate = LocalDate.parse(tilogList.get(i).getRegYmd(), FORMATTER);
			LocalDate prevDate = LocalDate.parse(tilogList.get(i - 1).getRegYmd(), FORMATTER);

			if (currDate.plusDays(1).equals(prevDate)) {
				consecutiveDays++;

				maxConsecutiveDays = Math.max(maxConsecutiveDays, consecutiveDays);
			} else {
				consecutiveDays = 1;
			}
		}

		return maxConsecutiveDays;
	}

}
