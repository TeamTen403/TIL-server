package com.teamten.til.challenge.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teamten.til.challenge.dto.ChallengeInfo;
import com.teamten.til.challenge.dto.ChallengeInfoResponse;
import com.teamten.til.challenge.service.ChallengeService;
import com.teamten.til.common.dto.ResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/challenge")
public class ChallengeController {

	private final ChallengeService challengeService;

	@GetMapping
	@Operation(description = "챌린지리스트 조회 API")
	public ResponseEntity<ResponseDto<ChallengeInfoResponse>> getChallengeAll() {
		// 로그인
		String tilerId = "a";
		return ResponseEntity.ok(ResponseDto.ok(challengeService.getChallengeList(tilerId)));
	}

	@PostMapping("/{challengeId}")
	@Operation(description = "챌린지 참가 API")
	public ResponseEntity<ResponseDto<ChallengeInfo>> participateChallenge(@PathVariable Long challengeId) {
		// 로그인
		String tilerId = "a";
		return ResponseEntity.ok(ResponseDto.ok(challengeService.applyChallenge(challengeId, tilerId)));
	}

}
