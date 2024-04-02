package com.teamten.til.challenge.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teamten.til.challenge.dto.ChallengeInfo;
import com.teamten.til.challenge.dto.ChallengeInfoResponse;
import com.teamten.til.challenge.service.ChallengeService;
import com.teamten.til.common.config.swagger.ApiErrorResponse;
import com.teamten.til.common.dto.LoginUser;
import com.teamten.til.common.dto.ResponseDto;
import com.teamten.til.common.dto.ResponseType;
import com.teamten.til.tiler.dto.LoginTiler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Challenge", description = "챌린지 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/challenge")
public class ChallengeController {

	private final ChallengeService challengeService;

	@GetMapping
	@Operation(description = "챌린지리스트 조회 API")
	@ApiResponse(responseCode = "200", description = "성공")
	@ApiErrorResponse(value = ResponseType.class, errorCodes = {401, 500})
	public ResponseEntity<ResponseDto<ChallengeInfoResponse>> getChallengeAll(@LoginUser LoginTiler tilerInfo) {
		// 로그인
		String tilerId = tilerInfo.getId().toString();
		return ResponseEntity.ok(ResponseDto.ok(challengeService.getChallengeList(tilerId)));
	}

	@PostMapping("/{challengeId}")
	@Operation(description = "챌린지 참가 API")
	@ApiResponse(responseCode = "200", description = "성공")
	@ApiErrorResponse(value = ResponseType.class, errorCodes = {201, 401, 404, 500})
	public ResponseEntity<ResponseDto<ChallengeInfo>> participateChallenge(@PathVariable Long challengeId) {
		// 로그인
		String tilerId = "a";
		return ResponseEntity.ok(ResponseDto.ok(challengeService.applyChallenge(challengeId, tilerId)));
	}

	@PutMapping("/{challengeId}")
	@Operation(description = "챌린지결과 업데이트")
	@ApiResponse(responseCode = "200", description = "성공")
	@ApiErrorResponse(value = ResponseType.class, errorCodes = {201, 401, 404, 500})
	public ResponseEntity<ResponseDto<ChallengeInfo>> updateChallengeResult(@PathVariable Long challengeId) {
		// 로그인
		String tilerId = "a";

		return ResponseEntity.ok(ResponseDto.ok(challengeService.updateChallengeResult(challengeId, tilerId)));
	}

}
