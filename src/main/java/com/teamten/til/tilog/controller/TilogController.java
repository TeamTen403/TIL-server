package com.teamten.til.tilog.controller;

import java.time.LocalDate;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

import com.teamten.til.common.config.swagger.ApiErrorResponse;
import com.teamten.til.common.dto.ResponseDto;
import com.teamten.til.common.dto.ResponseType;
import com.teamten.til.common.util.StorageUploader;
import com.teamten.til.tiler.entity.AuthUser;
import com.teamten.til.tiler.entity.LoginUser;
import com.teamten.til.tilog.dto.FeedResponse;
import com.teamten.til.tilog.dto.FileUploadResponse;
import com.teamten.til.tilog.dto.TagInfoResponse;
import com.teamten.til.tilog.dto.TilogInfo;
import com.teamten.til.tilog.dto.TilogMonthly;
import com.teamten.til.tilog.dto.TilogRequest;
import com.teamten.til.tilog.service.TagService;
import com.teamten.til.tilog.service.TilogService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Tilog", description = "틸로그 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tilog")
public class TilogController {
	private static final String TILOG_IMAGE_DIR = "tilog";
	private final TilogService tilogService;

	private final TagService tagService;
	private final StorageUploader storageUploader;

	@GetMapping
	@Operation(description = "월별 Tilog 리스트 조회")
	@ApiResponse(responseCode = "200", description = "성공")
	@ApiErrorResponse(value = ResponseType.class, errorCodes = {400, 500})
	public ResponseEntity<ResponseDto<TilogMonthly>> getMonthlyList(
		@RequestParam(required = false) @DateTimeFormat(pattern = "yyyyMM") LocalDate yyyyMM,
		@AuthUser LoginUser loginUser) {

		if (Objects.isNull(yyyyMM)) {
			yyyyMM = LocalDate.now();
		}

		return ResponseEntity.ok(ResponseDto.ok(tilogService.getMontlyList(loginUser, yyyyMM)));
	}

	@GetMapping("/feed")
	@Operation(description = "피드 리스트 조회")
	@ApiResponse(responseCode = "200", description = "성공")
	@ApiErrorResponse(value = ResponseType.class, errorCodes = {400, 500})
	public ResponseEntity<ResponseDto<FeedResponse>> getFeedList(@AuthenticationPrincipal LoginUser loginUser) {
		
		return ResponseEntity.ok(ResponseDto.ok(tilogService.getFeed(loginUser)));
	}

	@PostMapping("/image")
	@Operation(description = "이미지 업로드")
	@ApiResponse(responseCode = "200", description = "성공")
	@ApiErrorResponse(value = ResponseType.class)
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
	@ApiResponse(responseCode = "200", description = "성공")
	@ApiErrorResponse(value = ResponseType.class)
	public ResponseEntity<ResponseDto<TagInfoResponse>> getTagList() {
		return ResponseEntity.ok(ResponseDto.ok(tagService.getAll()));
	}

	@PostMapping
	@Operation(description = "tilog 작성")
	@ApiResponse(responseCode = "200", description = "성공")
	@ApiErrorResponse(value = ResponseType.class, errorCodes = {201, 400, 401, 500})
	public ResponseEntity<ResponseDto<TilogInfo>> postTilog(@Valid @RequestBody TilogRequest request,
		@AuthUser LoginUser loginUser) {

		return ResponseEntity.ok(ResponseDto.ok(tilogService.saveTilog(loginUser, request)));
	}

	@PutMapping("/{tilogId}")
	@Operation(description = "tilog 편집")
	@ApiResponse(responseCode = "200", description = "성공")
	@ApiErrorResponse(value = ResponseType.class, errorCodes = {400, 401, 404, 500})
	public ResponseEntity<ResponseDto<TilogInfo>> editTilog(
		@RequestBody TilogRequest request,
		@PathVariable Long tilogId,
		@AuthUser LoginUser loginUser) {

		return ResponseEntity.ok(ResponseDto.ok(tilogService.editTilog(loginUser, tilogId, request)));
	}

	@DeleteMapping("/{tilogId}")
	@Operation(description = "tilog 삭제")
	@ApiResponse(responseCode = "200", description = "성공")
	@ApiErrorResponse(value = ResponseType.class, errorCodes = {400, 401, 404, 500})
	public ResponseEntity<ResponseDto<Boolean>> removeTilog(@PathVariable Long tilogId,
		@AuthUser LoginUser loginUser) {

		tilogService.removeTilog(loginUser, tilogId);
		return ResponseEntity.ok(ResponseDto.ok(true));
	}

}
