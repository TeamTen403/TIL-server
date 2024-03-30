package com.teamten.til.tiler.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teamten.til.common.dto.ResponseDto;
import com.teamten.til.tiler.dto.TilerJoinRequest;
import com.teamten.til.tiler.dto.TilerLoginRequest;
import com.teamten.til.tiler.dto.TilerStatistics;
import com.teamten.til.tiler.entity.Tiler;
import com.teamten.til.tiler.service.TilerService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tiler")
public class TilerController {

	private final TilerService tilerService;

	@GetMapping
	public ResponseEntity<ResponseDto<List<Tiler>>> get() {
		return ResponseEntity.ok(ResponseDto.ok(tilerService.get()));
	}

	@GetMapping("/{tilerId}")
	public ResponseEntity<ResponseDto<Tiler>> get(@PathVariable UUID tilerId) {
		return ResponseEntity.ok(ResponseDto.ok(tilerService.getOne(tilerId)));
	}

	@PostMapping("/login")
	public ResponseEntity<String> log(@RequestBody TilerLoginRequest dto) {
		String token = tilerService.login(dto.getEmail(), dto.getPasswd());
		return ResponseEntity.ok().body(token);
	}

	@PostMapping("/join")
	public ResponseEntity<String> join(@RequestBody TilerJoinRequest dto) {
		tilerService.join(dto);
		return ResponseEntity.ok().body("회원가입 성공");
	}

	// TODO: 로그인 체크는 추가 필요
	@GetMapping("/statistics")
	@Operation(description = "유저통계 조회 API")
	public ResponseEntity<ResponseDto<TilerStatistics>> getStatistics() {
		String tilerId = "temp";

		return ResponseEntity.ok(ResponseDto.ok(tilerService.getStatistics(tilerId)));
	}
}
