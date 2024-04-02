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
import com.teamten.til.common.dto.ExceptionResponse;
import com.teamten.til.common.dto.LoginUser;
import com.teamten.til.common.dto.ResponseDto;
import com.teamten.til.tiler.dto.LoginTiler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/challenge")
public class ChallengeController {

	private final ChallengeService challengeService;

	@GetMapping
	@Operation(description = "챌린지리스트 조회 API")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "성공"),
		@ApiResponse(responseCode = "401", description = "권한없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))),
		@ApiResponse(responseCode = "500", description = "서버 에러", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class)))
	})
	public ResponseEntity<ResponseDto<ChallengeInfoResponse>> getChallengeAll(@LoginUser LoginTiler tilerInfo) {
		// 로그인
		String tilerId = tilerInfo.getId().toString();
		return ResponseEntity.ok(ResponseDto.ok(challengeService.getChallengeList(tilerId)));
	}

	@PostMapping("/{challengeId}")
	@Operation(description = "챌린지 참가 API")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "성공"),
		@ApiResponse(responseCode = "201", description = "이미 저장된 케이스(중복요청)", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))),
		@ApiResponse(responseCode = "401", description = "권한없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))),
		@ApiResponse(responseCode = "404", description = "챌린지가 존재하지 않음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))),
		@ApiResponse(responseCode = "500", description = "서버 에러", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class)))
	})
	public ResponseEntity<ResponseDto<ChallengeInfo>> participateChallenge(@PathVariable Long challengeId) {
		// 로그인
		String tilerId = "a";
		return ResponseEntity.ok(ResponseDto.ok(challengeService.applyChallenge(challengeId, tilerId)));
	}

	@PutMapping("/{challengeId}")
	@Operation(description = "챌린지결과 업데이트")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "성공"),
		@ApiResponse(responseCode = "201", description = "이미 업데이트된 케이스(중복요청)", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))),
		@ApiResponse(responseCode = "401", description = "권한없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))),
		@ApiResponse(responseCode = "404", description = "챌린지 내역이 존재하지 않음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))),
		@ApiResponse(responseCode = "500", description = "서버 에러", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class)))
	})
	public ResponseEntity<ResponseDto<ChallengeInfo>> updateChallengeResult(@PathVariable Long challengeId) {
		// 로그인
		String tilerId = "a";

		return ResponseEntity.ok(ResponseDto.ok(challengeService.updateChallengeResult(challengeId, tilerId)));
	}

}
