package com.teamten.til.challenge.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teamten.til.challenge.entity.Challenge;
import com.teamten.til.challenge.entity.ChallengeParticipant;
import com.teamten.til.tiler.entity.TilerTemp;

public interface ChallengeParticipantRepository extends JpaRepository<ChallengeParticipant, Long> {
	Optional<ChallengeParticipant> findByChallengeAndTiler(Challenge challenge, TilerTemp tiler);
}

