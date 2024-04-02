package com.teamten.til.challenge.dto;

import java.util.Objects;

import com.teamten.til.challenge.entity.Challenge;
import com.teamten.til.challenge.entity.ChallengeType;
import com.teamten.til.challenge.entity.MissionType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ChallengeInfo {
	@Schema(description = "챌린지 ID", defaultValue = "1")
	private Long challengeId;
	@Schema(description = "아이콘 URL", defaultValue = "https://teamten403.kr.object.ncloudstorage.com/tilog/fc89acb0-3aa4-4f3c-b821-6c6da62a175d.png")
	private String icon; // 챌린지 아이콘
	@Schema(description = "챌린지명", defaultValue = "챌린지명")
	private String title;
	@Schema(description = "챌린지 설명", defaultValue = "챌린지 설명")
	private String description;
	@Schema(description = "챌린지 미션", defaultValue = "챌린지 미션")
	private String mission;
	@Schema(description = "챌린지 레벨(상시)", defaultValue = "1")
	private Long level;
	@Schema(description = "미션 목표개수", defaultValue = "10")
	private int targetAmount;
	@Schema(description = "태그명", defaultValue = "백엔드")
	private String tagName;
	@Schema(description = "미션 타입(누적, 연속)", defaultValue = "ACCUMULATE", allowableValues = {"ACCUMULATE", "CONTINUITY"})
	private MissionType missionType; // 누적, 연속
	@Schema(description = "챌린지 타입(상시, 이벤트)", defaultValue = "NORMAL", allowableValues = {"NORMAL", "EVENT"})
	private ChallengeType challengeType;
	@Schema(description = "미션 성공 시 지급점수", defaultValue = "10")
	private int winningScore; // 점수
	@Schema(description = "미션 시작일자", defaultValue = "yyyy-MM-dd")
	private String startDate;
	@Schema(description = "미션 종료일자", defaultValue = "yyyy-MM-dd")
	private String endDate;
	@Schema(description = "유저의 현재 참여도", defaultValue = "0")
	private int myAmount;
	@Schema(description = "챌린지 참여여부", defaultValue = "false")
	private Boolean isParticipant;
	@Schema(description = "챌린지 종료여부", defaultValue = "false")
	private Boolean isEnd;

	@Schema(description = "챌린지 결과 업데이트 여부(isEnd가 true라면 업데이트 필요)", defaultValue = "false")
	private Boolean isUpdatedResult;

	public static ChallengeInfo of(Challenge challenge, boolean isParticipant, int myAmount, Boolean isSuccess) {
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
			.isUpdatedResult(!Objects.isNull(isSuccess))
			.build();
	}

}
