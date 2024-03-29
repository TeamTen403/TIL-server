package com.teamten.til.tilog.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BookmarkResponse {
	private Long tilogId;
	private Boolean isBookmarked;
}
