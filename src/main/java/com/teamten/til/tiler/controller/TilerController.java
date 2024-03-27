package com.teamten.til.tiler.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teamten.til.common.dto.ResponseDto;
import com.teamten.til.tiler.dto.TilerStatistics;
import com.teamten.til.tiler.service.TilerService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tiler")
public class TilerController {
	private final TilerService tilerService;

	// TODO: 로그인 체크는 추가 필요
	@GetMapping("/statistics")
	@Operation(description = "유저통계 조회 API")
	public ResponseEntity<ResponseDto<TilerStatistics>> getStatistics() {
		String loginInfo = "temp";

		return ResponseEntity.ok(ResponseDto.ok(tilerService.getStatistics(loginInfo)));
	}
}