package com.teamten.til.tilog.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teamten.til.common.config.swagger.ApiErrorResponse;
import com.teamten.til.common.dto.ResponseDto;
import com.teamten.til.common.dto.ResponseType;
import com.teamten.til.tilog.dto.BookmarkResponse;
import com.teamten.til.tilog.dto.TilogInfoResponse;
import com.teamten.til.tilog.service.BookmarkService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Bookmark", description = "북마크 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tilog")
public class BookmarkController {
	private final BookmarkService bookmarkService;

	@PostMapping("/{tilogId}/bookmark")
	@Operation(description = "북마크 저장")
	@ApiResponse(responseCode = "200", description = "성공")
	@ApiErrorResponse(value = ResponseType.class, errorCodes = {201, 401, 404, 500})
	public ResponseEntity<ResponseDto<BookmarkResponse>> addBookmark(@PathVariable Long tilogId) {
		String tilerId = "tilerId";
		return ResponseEntity.ok(ResponseDto.ok(bookmarkService.addBookmark(tilerId, tilogId)));
	}

	@DeleteMapping("/{tilogId}/bookmark")
	@Operation(description = "북마크 취소")
	@ApiResponse(responseCode = "200", description = "성공")
	@ApiErrorResponse(value = ResponseType.class, errorCodes = {201, 401, 500})
	public ResponseEntity<ResponseDto<BookmarkResponse>> removeBookmark(@PathVariable Long tilogId) {
		// TODO: 로그인정보
		String tilerId = "tilerId";
		return ResponseEntity.ok(ResponseDto.ok(bookmarkService.removeBookmark(tilerId, tilogId)));
	}

	@GetMapping("/bookmark")
	@Operation(description = "북마크한 틸로그 리스트 조회")
	@ApiResponse(responseCode = "200", description = "성공")
	@ApiErrorResponse(value = ResponseType.class, errorCodes = {401, 500})
	public ResponseEntity<ResponseDto<TilogInfoResponse>> getBookmarkList() {
		String tilerId = "tilerId";
		return ResponseEntity.ok(ResponseDto.ok(bookmarkService.getAllMyBookmark(tilerId)));
	}
}
