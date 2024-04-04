package com.teamten.til.tiler.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teamten.til.common.config.swagger.ApiErrorResponse;
import com.teamten.til.common.dto.ResponseDto;
import com.teamten.til.common.dto.ResponseType;
import com.teamten.til.tiler.dto.TilerInfo;
import com.teamten.til.tiler.dto.TilerJoinRequest;
import com.teamten.til.tiler.dto.TilerLoginRequest;
import com.teamten.til.tiler.dto.TilerLoginResponse;
import com.teamten.til.tiler.dto.TilerStatistics;
import com.teamten.til.tiler.entity.AuthUser;
import com.teamten.til.tiler.entity.LoginUser;
import com.teamten.til.tiler.service.TilerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Tag(name = "Tiler", description = "유저 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tiler")
public class TilerController {

	private final TilerService tilerService;

	@GetMapping
	@Operation(description = "회원정보 조회")
	@ApiResponse(responseCode = "200", description = "성공")
	@ApiErrorResponse(value = ResponseType.class, errorCodes = {401, 404, 500})
	public ResponseEntity<ResponseDto<TilerInfo>> getInfo(@AuthUser LoginUser loginUser) {
		return ResponseEntity.ok(ResponseDto.ok(tilerService.getOne(loginUser.getId())));
	}

	@PostMapping("/sign-in")
	@Operation(description = "로그인")
	@ApiResponse(responseCode = "200", description = "성공")
	@ApiErrorResponse(value = ResponseType.class, errorCodes = {400, 404, 500})
	public ResponseEntity<ResponseDto<TilerLoginResponse>> signIn(@RequestBody TilerLoginRequest request,
		HttpServletResponse response) {
		return ResponseEntity.ok(ResponseDto.ok(tilerService.login(response, request)));
	}

	@PostMapping("/sign-up")
	@Operation(description = "회원가입")
	@ApiResponse(responseCode = "200", description = "성공")
	@ApiErrorResponse(value = ResponseType.class, errorCodes = {201, 400, 404, 500})
	public ResponseEntity<ResponseDto<Boolean>> join(@RequestBody TilerJoinRequest request) {
		tilerService.join(request);
		return ResponseEntity.ok(ResponseDto.ok(true));
	}

	@GetMapping("/statistics")
	@Operation(description = "유저통계 조회 API")
	@ApiResponse(responseCode = "200", description = "성공")
	@ApiErrorResponse(value = ResponseType.class, errorCodes = {401, 500})
	public ResponseEntity<ResponseDto<TilerStatistics>> getStatistics(@AuthUser LoginUser loginUser) {
		return ResponseEntity.ok(ResponseDto.ok(tilerService.getStatistics(loginUser)));
	}
}
