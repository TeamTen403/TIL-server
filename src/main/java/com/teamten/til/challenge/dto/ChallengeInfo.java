package com.teamten.til.challenge.dto;

import org.hibernate.annotations.ColumnDefault;

import com.teamten.til.challenge.entity.Challenge;
import com.teamten.til.challenge.entity.ChallengeType;
import com.teamten.til.challenge.entity.MissionType;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ChallengeInfo {
	@ColumnDefault("'N'")
	private Character accumulateScoreYn;

	private Long challengeId;
	private String icon; // 챌린지 아이콘
	private String title;
	private String description;
	private String mission;
	private Long level; // 상시에 포함
	private int targetAmount;
	private String tagName;
	private MissionType missionType; // 누적, 연속
	private ChallengeType challengeType; // 누적, 연속
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
			.mission(challenge.getMission())
			.missionType(challenge.getMissionType())
			.challengeType(challenge.getChallengeType())
			.winningScore(challenge.getWinningScore())
			.level(challenge.getLevel())
			.targetAmount(challenge.getTargetAmount())
			.tagName(challenge.getTagName())
			.startDate(challenge.getStartDate())
			.endDate(challenge.getEndDate())
			.isParticipant(isParticipant)
			.myAmount(myAmount)
			.isEnd(challenge.isEnd())
			.build();
	}

}
