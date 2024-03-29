package com.teamten.til.tilog.dto;

import com.teamten.til.tilog.entity.Tag;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TagInfo {
	private String tagId;
	private String tagName;

	public static TagInfo from(Tag tag) {
		return TagInfo.builder()
			.tagId(tag.getId())
			.tagName(tag.getName())
			.build();
	}
}
