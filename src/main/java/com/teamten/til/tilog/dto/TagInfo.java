package com.teamten.til.tilog.dto;

import com.teamten.til.tilog.entity.Tag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TagInfo {
	@Schema(description = "태그 ID", defaultValue = "BE")
	private String tagId;
	@Schema(description = "태그명", defaultValue = "백엔드")
	private String tagName;

	public static TagInfo from(Tag tag) {
		return TagInfo.builder()
			.tagId(tag.getId())
			.tagName(tag.getName())
			.build();
	}
}
