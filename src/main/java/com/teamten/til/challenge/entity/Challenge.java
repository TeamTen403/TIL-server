package com.teamten.til.challenge.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Challenge {
	@Id
	private Long id;
	private String title;
	private String description;
	private String mission;
	private int missionCount;
	private String tagId;
	private String type; // 누적, 연속
	private int winningScore; // 점수
	private LocalDateTime startYmdt;
	private LocalDateTime endYmdt;

}
