package com.teamten.til.challenge.entity;

import com.teamten.til.common.BaseTimeEntity;
import com.teamten.til.tiler.entity.Tiler;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChallengeParticipant extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JoinColumn(name = "tiler_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private Tiler tiler;

	@JoinColumn(name = "challenge_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private Challenge challenge;

	private Boolean isSuccess;

	private int score;

	public void updateResult(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

}
