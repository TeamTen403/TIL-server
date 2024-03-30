package com.teamten.til.tilog.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teamten.til.common.dto.ResponseDto;
import com.teamten.til.tilog.dto.LikeResponse;
import com.teamten.til.tilog.service.LikesService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tilog")
public class LikesController {
	private final LikesService likesService;

	@PostMapping("/{tilogId}/like")
	@Operation(description = "좋아요 저장")
	public ResponseEntity<ResponseDto<LikeResponse>> addLikes(@PathVariable Long tilogId) {
		String tilerId = "tilerId";
		return ResponseEntity.ok(ResponseDto.ok(likesService.addLikes(tilerId, tilogId)));
	}

	@DeleteMapping("/{tilogId}/like")
	@Operation(description = "좋아요 취소")
	public ResponseEntity<ResponseDto<LikeResponse>> removeLikes(@PathVariable Long tilogId) {
		// TODO: 로그인정보
		String tilerId = "tilerId";
		return ResponseEntity.ok(ResponseDto.ok(likesService.removeLikes(tilerId, tilogId)));
	}
}