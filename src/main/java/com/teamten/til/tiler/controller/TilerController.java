package com.teamten.til.tiler.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teamten.til.common.dto.ResponseDto;
import com.teamten.til.tiler.dto.TilerInfo;
import com.teamten.til.tiler.dto.TilerJoinRequest;
import com.teamten.til.tiler.dto.TilerLoginRequest;
import com.teamten.til.tiler.dto.TilerLoginResponse;
import com.teamten.til.tiler.dto.TilerStatistics;
import com.teamten.til.tiler.entity.AuthUser;
import com.teamten.til.tiler.entity.LoginUser;
import com.teamten.til.tiler.service.TilerService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tiler")
public class TilerController {

	private final TilerService tilerService;

	@GetMapping
	public ResponseEntity<ResponseDto<TilerInfo>> getInfo(@AuthUser LoginUser loginUser) {
		return ResponseEntity.ok(ResponseDto.ok(tilerService.getOne(loginUser.getId())));
	}

	@PostMapping("/sign-in")
	public ResponseEntity<ResponseDto<TilerLoginResponse>> signIn(@RequestBody TilerLoginRequest request,
		HttpServletResponse response) {
		return ResponseEntity.ok(ResponseDto.ok(tilerService.login(response, request)));
	}

	@PostMapping("/sign-up")
	public ResponseEntity<String> join(@RequestBody TilerJoinRequest request) {
		tilerService.join(request);
		return ResponseEntity.ok().body("회원가입 성공");
	}

	@GetMapping("/statistics")
	@Operation(description = "유저통계 조회 API")
	public ResponseEntity<ResponseDto<TilerStatistics>> getStatistics(@AuthUser LoginUser loginUser) {
		return ResponseEntity.ok(ResponseDto.ok(tilerService.getStatistics(loginUser)));
	}
}
