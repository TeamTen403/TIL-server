package com.teamten.til.tilog.controller;

import java.time.LocalDate;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.teamten.til.common.dto.ResponseDto;
import com.teamten.til.common.dto.ResponseType;
import com.teamten.til.common.util.StorageUploader;
import com.teamten.til.tilog.dto.FileUploadResponse;
import com.teamten.til.tilog.dto.TilogMonthly;
import com.teamten.til.tilog.service.TILogService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tilog")
public class TILogController {
	private static final String TILOG_IMAGE_DIR = "tilog";
	private final TILogService tiLogService;
	private final StorageUploader storageUploader;

	@GetMapping
	@Operation(description = "월별 tilog 리스트 조회")
	public ResponseEntity<ResponseDto<TilogMonthly>> getMonthlyList(
		@RequestParam(required = false) @DateTimeFormat(pattern = "yyyyMM") LocalDate ym) {

		if (Objects.isNull(ym)) {
			ym = LocalDate.now();
		}

		return ResponseEntity.ok(ResponseDto.ok(tiLogService.getMontlyList(ym)));
	}

	@PostMapping("/image")
	@Operation(description = "이미지 업로드")
	public ResponseEntity<ResponseDto<FileUploadResponse>> uploadImage(@RequestParam MultipartFile image) {
		try {
			FileUploadResponse fileUploadResponse = storageUploader.upload(image, TILOG_IMAGE_DIR);
			return ResponseEntity.ok(ResponseDto.ok(fileUploadResponse));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseDto.code(ResponseType.ERROR));
		}

	}
}
