package com.teamten.til.challenge.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ChallengeInfoResponse {
	List<ChallengeInfo> challengeInfos;
}
