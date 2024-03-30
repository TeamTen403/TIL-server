package com.teamten.til.tilog.controller;

import java.time.LocalDate;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.teamten.til.common.dto.ResponseDto;
import com.teamten.til.common.dto.ResponseType;
import com.teamten.til.common.util.StorageUploader;
import com.teamten.til.tilog.dto.FeedResponse;
import com.teamten.til.tilog.dto.FileUploadResponse;
import com.teamten.til.tilog.dto.TagInfoResponse;
import com.teamten.til.tilog.dto.TilogInfo;
import com.teamten.til.tilog.dto.TilogMonthly;
import com.teamten.til.tilog.dto.TilogRequest;
import com.teamten.til.tilog.service.TagService;
import com.teamten.til.tilog.service.TilogService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tilog")
public class TilogController {
	private static final String TILOG_IMAGE_DIR = "tilog";
	private final TilogService tilogService;
	
	private final TagService tagService;
	private final StorageUploader storageUploader;

	@GetMapping
	@Operation(description = "월별 tilog 리스트 조회")
	public ResponseEntity<ResponseDto<TilogMonthly>> getMonthlyList(
		@RequestParam(required = false) @DateTimeFormat(pattern = "yyyyMM") LocalDate yyyyMM) {

		if (Objects.isNull(yyyyMM)) {
			yyyyMM = LocalDate.now();
		}

		// TODO: 로그인정보
		String tilerId = "tilerId";

		return ResponseEntity.ok(ResponseDto.ok(tilogService.getMontlyList(yyyyMM, tilerId)));
	}

	@GetMapping("/feed")
	@Operation(description = "피드 리스트 조회")
	public ResponseEntity<ResponseDto<FeedResponse>> getFeedList() {
		// TODO: 로그인정보
		String tilerId = "tilerId";

		return ResponseEntity.ok(ResponseDto.ok(tilogService.getFeed(tilerId)));
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

	@GetMapping("/tag")
	@Operation(description = "태그리스트 조회")
	public ResponseEntity<ResponseDto<TagInfoResponse>> getTagList() {
		return ResponseEntity.ok(ResponseDto.ok(tagService.getAll()));
	}

	@PostMapping
	@Operation(description = "tilog 작성")
	public ResponseEntity<ResponseDto<TilogInfo>> postTilog(@RequestBody TilogRequest request) {
		// TODO: 로그인정보
		String tilerId = "tilerId";
		return ResponseEntity.ok(ResponseDto.ok(tilogService.saveTilog(request, tilerId)));
	}

	@PutMapping("/{tilogId}")
	@Operation(description = "tilog 편집")
	public ResponseEntity<ResponseDto<TilogInfo>> editTilog(
		@RequestBody TilogRequest request,
		@PathVariable Long tilogId) {
		// TODO: 로그인정보
		String tilerId = "tilerId";
		return ResponseEntity.ok(ResponseDto.ok(tilogService.editTilog(tilogId, request, tilerId)));
	}

	@DeleteMapping("/{tilogId}")
	@Operation(description = "tilog 삭제")
	public ResponseEntity<ResponseDto> removeTilog(@PathVariable Long tilogId) {
		// TODO: 로그인정보
		String tilerId = "tilerId";
		tilogService.removeTilog(tilogId, tilerId);
		return ResponseEntity.ok(ResponseDto.ok());
	}

}
