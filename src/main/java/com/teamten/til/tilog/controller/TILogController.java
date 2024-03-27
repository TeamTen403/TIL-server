package com.teamten.til.tilog.controller;

import java.time.LocalDate;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.teamten.til.common.dto.ResponseDto;
import com.teamten.til.tilog.dto.TilogMonthly;
import com.teamten.til.tilog.service.TILogService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tilog")
public class TILogController {
	private final TILogService tiLogService;

	@Operation(description = "월별 tilog 리스트 조회")
	public ResponseEntity<ResponseDto<TilogMonthly>> getMonthlyList(
		@RequestParam(required = false) @DateTimeFormat(pattern = "yyyyMM") LocalDate ym) {

		if (Objects.isNull(ym)) {
			ym = LocalDate.now();
		}

		return ResponseEntity.ok(ResponseDto.ok(tiLogService.getMontlyList(ym)));
	}
}
