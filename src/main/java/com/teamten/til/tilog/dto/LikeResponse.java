package com.teamten.til.tilog.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LikeResponse {
	private Long tilogId;
	private Long likes;
	private Boolean isLiked;
}
