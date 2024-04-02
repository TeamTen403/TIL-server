package com.teamten.til.challenge.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

import org.hibernate.annotations.ColumnDefault;

import com.teamten.til.tilog.entity.Tag;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Challenge {
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String icon; // 챌린지 아이콘
	private String title; // 챌린지 제목
	private String description; // 챌린지 설명
	private String mission; // 미션설명
	private Long level; // 상시에 포함
	private int targetAmount; // 미션
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Tag tag;
	@NonNull
	@Enumerated(EnumType.STRING)
	private MissionType missionType; // 누적, 연속

	@NonNull
	@Enumerated(EnumType.STRING)
	private ChallengeType challengeType; // 상시, 이벤트
	private int winningScore; // 점수
	private LocalDate startYmd;
	private LocalDate endYmd;

	@ColumnDefault("'N'")
	private Character accumulateScoreYn;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "challenge", cascade = CascadeType.ALL)
	private List<ChallengeParticipant> challengeParticipants;

	public String getTagName() {
		if (Objects.isNull(tag)) {
			return null;
		}
		return tag.getName();
	}

	public String getStartDate() {
		return startYmd.format(FORMATTER);
	}

	public String getEndDate() {
		return endYmd.format(FORMATTER);
	}

	public boolean isEnd() {
		return LocalDate.now().isAfter(endYmd);
	}

	public boolean inProgress() {
		LocalDate now = LocalDate.now();
		return !now.isAfter(endYmd) && !now.isBefore(startYmd);
	}
}
