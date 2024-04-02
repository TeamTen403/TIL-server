package com.teamten.til.tilog.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teamten.til.common.dto.ExceptionResponse;
import com.teamten.til.common.dto.ResponseDto;
import com.teamten.til.tilog.dto.LikeResponse;
import com.teamten.til.tilog.service.LikesService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tilog")
public class LikesController {
	private final LikesService likesService;

	@PostMapping("/{tilogId}/like")
	@Operation(description = "좋아요 저장")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "성공"),
		@ApiResponse(responseCode = "201", description = "이미 저장된 케이스(중복요청)", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))),
		@ApiResponse(responseCode = "401", description = "권한없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))),
		@ApiResponse(responseCode = "404", description = "Tilog가 존재하지 않음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))),
		@ApiResponse(responseCode = "500", description = "서버 에러", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class)))
	})
	public ResponseEntity<ResponseDto<LikeResponse>> addLikes(@PathVariable Long tilogId) {
		String tilerId = "tilerId";
		return ResponseEntity.ok(ResponseDto.ok(likesService.addLikes(tilerId, tilogId)));
	}

	@DeleteMapping("/{tilogId}/like")
	@Operation(description = "좋아요 취소")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "성공"),
		@ApiResponse(responseCode = "201", description = "이미 취소된 케이스(중복요청)", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))),
		@ApiResponse(responseCode = "401", description = "권한없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))),
		@ApiResponse(responseCode = "404", description = "Tilog가 존재하지 않음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))),
		@ApiResponse(responseCode = "500", description = "서버 에러", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class)))
	})
	public ResponseEntity<ResponseDto<LikeResponse>> removeLikes(@PathVariable Long tilogId) {
		// TODO: 로그인정보
		String tilerId = "tilerId";
		return ResponseEntity.ok(ResponseDto.ok(likesService.removeLikes(tilerId, tilogId)));
	}
}
