package com.teamten.til.tilog.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teamten.til.common.dto.ResponseDto;
import com.teamten.til.tilog.dto.BookmarkResponse;
import com.teamten.til.tilog.dto.TilogInfoResponse;
import com.teamten.til.tilog.service.BookmarkService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tilog")
public class BookmarkController {
	private final BookmarkService bookmarkService;

	@PostMapping("/{tilogId}/bookmark")
	@Operation(description = "북마크 저장")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "성공"),
		@ApiResponse(responseCode = "201", description = "이미 저장된 케이스(중복요청)", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
		@ApiResponse(responseCode = "401", description = "비로그인", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
		@ApiResponse(responseCode = "404", description = "Tilog가 존재하지 않음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
		@ApiResponse(responseCode = "500", description = "서버 에러", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)))
	})
	public ResponseEntity<ResponseDto<BookmarkResponse>> addBookmark(@PathVariable Long tilogId) {
		String tilerId = "tilerId";
		return ResponseEntity.ok(ResponseDto.ok(bookmarkService.addBookmark(tilerId, tilogId)));
	}

	@DeleteMapping("/{tilogId}/bookmark")
	@Operation(description = "북마크 취소")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "성공"),
		@ApiResponse(responseCode = "201", description = "이미 취소된 케이스(중복요청)", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
		@ApiResponse(responseCode = "401", description = "비로그인", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
		@ApiResponse(responseCode = "500", description = "서버 에러", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)))
	})
	public ResponseEntity<ResponseDto<BookmarkResponse>> removeBookmark(@PathVariable Long tilogId) {
		// TODO: 로그인정보
		String tilerId = "tilerId";
		return ResponseEntity.ok(ResponseDto.ok(bookmarkService.removeBookmark(tilerId, tilogId)));
	}

	@GetMapping("/bookmark")
	@Operation(description = "북마크한 틸로그 리스트 조회")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "성공"),
		@ApiResponse(responseCode = "401", description = "비로그인", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
		@ApiResponse(responseCode = "500", description = "서버 에러", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)))
	})
	public ResponseEntity<ResponseDto<TilogInfoResponse>> getBookmarkList() {
		String tilerId = "tilerId";
		return ResponseEntity.ok(ResponseDto.ok(bookmarkService.getAllMyBookmark(tilerId)));
	}
}
