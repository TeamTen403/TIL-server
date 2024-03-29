package com.teamten.til.tiler.controller;

import com.teamten.til.tiler.dto.TilerJoinRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.teamten.til.common.dto.ResponseDto;
import com.teamten.til.tiler.dto.TilerStatistics;
import com.teamten.til.tiler.service.TilerService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

import static com.teamten.til.tiler.entity.AuthProvider.TIL_EMAIL;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tiler")
public class TilerController {

	private final TilerService tilerService;

	@PostMapping("/join")
	public ResponseEntity<String> join(@RequestBody TilerJoinRequest dto){
		tilerService.join(dto);
		return ResponseEntity.ok().body("회원가입 성공");
	}
	// TODO: 로그인 체크는 추가 필요
	@GetMapping("/statistics")
	@Operation(description = "유저통계 조회 API")
	public ResponseEntity<ResponseDto<TilerStatistics>> getStatistics() {
		String loginInfo = "temp";

		return ResponseEntity.ok(ResponseDto.ok(tilerService.getStatistics(loginInfo)));
	}
}
