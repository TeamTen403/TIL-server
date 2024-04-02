package com.teamten.til.tilog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LikeResponse {
	@Schema(description = "틸로그 ID", defaultValue = "1")
	private Long tilogId;
	@Schema(description = "좋아요 갯수", defaultValue = "300")
	private Long likes;
	@Schema(description = "좋아요 클릭여부", defaultValue = "false")
	private Boolean isLiked;
}
