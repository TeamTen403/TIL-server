package com.teamten.til.tilog.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.teamten.til.tilog.entity.Tilog;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TilogInfo implements Serializable {
	private long id;
	private String title;
	private String nickname;
	private String thumbnailUrl;
	private String tagName;
	private long likeCount;
	private LocalDateTime regYmdt;
	private LocalDateTime modYmdt;
	private Boolean isLiked;
	private Boolean isBookmarked;

	public static TilogInfo from(Tilog tilog) {
		return TilogInfo.builder()
			.id(tilog.getId())
			.title(tilog.getTitle())
			.nickname(tilog.getUser().getNickName())
			.thumbnailUrl(tilog.getThumbnail())
			.tagName(tilog.getTagName())
			.likeCount(tilog.getLikes())
			.regYmdt(tilog.getRegYmdt())
			.modYmdt(tilog.getModYmdt())
			.isLiked(false)
			.isBookmarked(false)
			.build();
	}

	public static TilogInfo of(Tilog tilog, boolean isLiked, boolean isBookmarked) {
		return TilogInfo.builder()
			.id(tilog.getId())
			.title(tilog.getTitle())
			.nickname(tilog.getUser().getNickName())
			.thumbnailUrl(tilog.getThumbnail())
			.tagName(tilog.getTagName())
			.likeCount(tilog.getLikes())
			.regYmdt(tilog.getRegYmdt())
			.modYmdt(tilog.getModYmdt())
			.isLiked(isLiked)
			.isBookmarked(isBookmarked)
			.build();
	}

}
