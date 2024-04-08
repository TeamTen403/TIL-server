package com.teamten.til.tilog.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teamten.til.common.config.swagger.ApiErrorResponse;
import com.teamten.til.common.dto.ResponseDto;
import com.teamten.til.common.dto.ResponseType;
import com.teamten.til.tiler.entity.AuthUser;
import com.teamten.til.tiler.entity.LoginUser;
import com.teamten.til.tilog.dto.LikeResponse;
import com.teamten.til.tilog.service.LikesService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Likes", description = "좋아요 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tilog")
public class LikesController {
	private final LikesService likesService;

	@PutMapping("/{tilogId}/like")
	@Operation(description = "좋아요 저장/취소")
	@ApiResponse(responseCode = "200", description = "성공")
	@ApiErrorResponse(value = ResponseType.class, errorCodes = {401, 404, 500})
	public ResponseEntity<ResponseDto<LikeResponse>> toggleLikes(@PathVariable Long tilogId,
		@AuthUser LoginUser loginUser) {

		return ResponseEntity.ok(ResponseDto.ok(likesService.toggleLikes(loginUser, tilogId)));
	}

}
