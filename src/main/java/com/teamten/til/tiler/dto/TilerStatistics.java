package com.teamten.til.tiler.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TilerStatistics {
	private int consecutiveDays;
	private int totalTilog;
	private int totalLikes;
	private int totalChallenge;

}
