package com.teamten.til.challenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teamten.til.challenge.entity.Challenge;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
}
