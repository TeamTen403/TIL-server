package com.teamten.til.tilog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BookmarkResponse {
	@Schema(description = "틸로그 ID", defaultValue = "1")
	private Long tilogId;
	@Schema(description = "북마크 클릭여부", defaultValue = "1")
	private Boolean isBookmarked;
}
