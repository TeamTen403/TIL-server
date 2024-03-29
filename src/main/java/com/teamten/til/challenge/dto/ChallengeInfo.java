package com.teamten.til.challenge.dto;

import com.teamten.til.challenge.entity.Challenge;
import com.teamten.til.challenge.entity.ChallengeType;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ChallengeInfo {
	private Long challengeId;
	private String title;
	private String description;
	private String mission;
	private int targetAmount;
	private String tagName;
	private ChallengeType type; // 누적, 연속
	private int winningScore; // 점수
	private String startDate;
	private String endDate;
	private int myAmount;
	private Boolean isParticipant;
	private Boolean isEnd;

	public static ChallengeInfo of(Challenge challenge, boolean isParticipant, int myAmount) {
		return ChallengeInfo.builder()
			.challengeId(challenge.getId())
			.title(challenge.getTitle())
			.description(challenge.getDescription())
			.tagName(challenge.getTagName())
			.targetAmount(challenge.getTargetAmount())
			.startDate(challenge.getStartDate())
			.endDate(challenge.getEndDate())
			.isParticipant(isParticipant)
			.myAmount(myAmount)
			.isEnd(challenge.isEnd())
			.build();
	}

}
