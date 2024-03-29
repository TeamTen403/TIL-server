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
import com.teamten.til.tilog.dto.BookmarkResponse;
import com.teamten.til.tilog.dto.FileUploadResponse;
import com.teamten.til.tilog.dto.LikeResponse;
import com.teamten.til.tilog.dto.TilogInfo;
import com.teamten.til.tilog.dto.TilogMonthly;
import com.teamten.til.tilog.dto.TilogRequest;
import com.teamten.til.tilog.entity.Tag;
import com.teamten.til.tilog.repository.TagRepository;
import com.teamten.til.tilog.service.BookmarkService;
import com.teamten.til.tilog.service.LikesService;
import com.teamten.til.tilog.service.TilogService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tilog")
public class TilogController {
	private static final String TILOG_IMAGE_DIR = "tilog";
	private final TilogService tilogService;
	private final LikesService likesService;
	private final BookmarkService bookmarkService;
	private final StorageUploader storageUploader;
	private final TagRepository tagRepository;

	@GetMapping
	@Operation(description = "월별 tilog 리스트 조회")
	public ResponseEntity<ResponseDto<TilogMonthly>> getMonthlyList(
		@RequestParam(required = false) @DateTimeFormat(pattern = "yyyyMM") LocalDate ym) {

		if (Objects.isNull(ym)) {
			ym = LocalDate.now();
		}

		// TODO: 로그인정보
		String email = "email";

		return ResponseEntity.ok(ResponseDto.ok(tilogService.getMontlyList(ym, email)));
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

	// TODO: 제거
	@PostMapping("/tag")
	public ResponseEntity<ResponseDto<Tag>> addTag(@RequestParam String name) {
		Tag tag = Tag.builder().name(name).build();

		tagRepository.save(tag);
		return ResponseEntity.ok(ResponseDto.ok(tag));
	}

	@PostMapping
	@Operation(description = "tilog 작성")
	public ResponseEntity<ResponseDto<TilogInfo>> postTilog(@RequestBody TilogRequest request) {
		// TODO: 로그인정보
		String email = "email";
		return ResponseEntity.ok(ResponseDto.ok(tilogService.saveTilog(request, email)));
	}

	@PutMapping("/{tilogId}")
	@Operation(description = "tilog 편집")
	public ResponseEntity<ResponseDto<TilogInfo>> editTilog(
		@RequestBody TilogRequest request,
		@PathVariable Long tilogId) {
		// TODO: 로그인정보
		String email = "email";
		return ResponseEntity.ok(ResponseDto.ok(tilogService.editTilog(tilogId, request, email)));
	}

	@DeleteMapping("/{tilogId}")
	@Operation(description = "tilog 편집")
	public ResponseEntity<ResponseDto> removeTilog(@PathVariable Long tilogId) {
		// TODO: 로그인정보
		String email = "email";
		tilogService.removeTilog(tilogId, email);
		return ResponseEntity.ok(ResponseDto.ok());
	}

	@PostMapping("/{tilogId}/like")
	@Operation(description = "좋아요 저장")
	public ResponseEntity<ResponseDto<LikeResponse>> addLikes(@PathVariable Long tilogId) {
		String email = "email";
		return ResponseEntity.ok(ResponseDto.ok(likesService.addLikes(email, tilogId)));
	}

	@DeleteMapping("/{tilogId}/like")
	@Operation(description = "좋아요 취소")
	public ResponseEntity<ResponseDto<LikeResponse>> removeLikes(@PathVariable Long tilogId) {
		// TODO: 로그인정보
		String email = "email";
		return ResponseEntity.ok(ResponseDto.ok(likesService.removeLikes(email, tilogId)));
	}

	@PostMapping("/{tilogId}/bookmark")
	@Operation(description = "북마크 저장")
	public ResponseEntity<ResponseDto<BookmarkResponse>> addBookmark(@PathVariable Long tilogId) {
		String email = "email";
		return ResponseEntity.ok(ResponseDto.ok(bookmarkService.addBookmark(email, tilogId)));
	}

	@DeleteMapping("/{tilogId}/bookmark")
	@Operation(description = "북마크 취소")
	public ResponseEntity<ResponseDto<BookmarkResponse>> removeBookmark(@PathVariable Long tilogId) {
		// TODO: 로그인정보
		String email = "email";
		return ResponseEntity.ok(ResponseDto.ok(bookmarkService.removeBookmark(email, tilogId)));
	}
}
