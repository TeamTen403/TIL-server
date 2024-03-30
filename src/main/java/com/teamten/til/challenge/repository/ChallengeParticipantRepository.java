package com.teamten.til.challenge.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teamten.til.challenge.entity.Challenge;
import com.teamten.til.challenge.entity.ChallengeParticipant;
import com.teamten.til.tiler.entity.Tiler;

public interface ChallengeParticipantRepository extends JpaRepository<ChallengeParticipant, Long> {
	Optional<ChallengeParticipant> findByChallengeAndTiler(Challenge challenge, Tiler tiler);

	List<ChallengeParticipant> findAllByTilerAAndIsSuccessTrue(Tiler tiler);
}

