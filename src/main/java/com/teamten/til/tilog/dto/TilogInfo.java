package com.teamten.til.tilog.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TilogInfo implements Serializable {
	private String id;
	private String title;
	private String nickname;
	private String thumbnailUrl;
	private String tagName;
	private long likeCount;
	private LocalDateTime regYmdt;
	private LocalDateTime modYmdt;
	private Boolean isLiked;
	private Boolean isBookmarked;

}
