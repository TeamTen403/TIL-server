package com.teamten.til.tilog.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.teamten.til.tilog.entity.Tilog;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TilogInfo implements Serializable {
	@Schema(description = "틸로그 ID", defaultValue = "1")
	private long tilogId;
	@Schema(description = "틸로그 제목", defaultValue = "틸로그 제목")
	private String title;
	@Schema(description = "틸로그 내용", defaultValue = "틸로그 내용")
	private String content;
	@Schema(description = "글쓴이 이메일", defaultValue = "글쓴이 에메일")
	private String tilerEmail;
	@Schema(description = "닉네임", defaultValue = "닉네임")
	private String nickname;
	@Schema(description = "썸네일 URL", defaultValue = "https://teamten403.kr.object.ncloudstorage.com/tilog/fc89acb0-3aa4-4f3c-b821-6c6da62a175d.png")
	private String thumbnailUrl;
	@Schema(description = "태그명", defaultValue = "백엔드")
	private String tagName;
	@Schema(description = "좋아요 갯수", defaultValue = "300")
	private long likeCount;
	@Schema(description = "생성일자")
	private LocalDateTime regYmdt;
	@Schema(description = "최근 수정일자")
	private LocalDateTime modYmdt;
	@Schema(description = "좋아요 클릭여부", defaultValue = "false")
	private Boolean isLiked;
	@Schema(description = "북마크 클릭여부", defaultValue = "false")
	private Boolean isBookmarked;

	public static TilogInfo from(Tilog tilog) {
		return TilogInfo.builder()
			.tilogId(tilog.getId())
			.title(tilog.getTitle())
			.content(tilog.getContent())
			.nickname(tilog.getTiler().getNickname())
			.tilerEmail(tilog.getTiler().getEmail())
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
			.tilogId(tilog.getId())
			.title(tilog.getTitle())
			.content(tilog.getContent())
			.nickname(tilog.getTiler().getNickname())
			.tilerEmail(tilog.getTiler().getEmail())
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
