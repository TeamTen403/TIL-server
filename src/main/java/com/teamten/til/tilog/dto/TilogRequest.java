package com.teamten.til.tilog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class TilogRequest {
	@NotEmpty
	@Schema(description = "제목", defaultValue = "제목")
	private String title;
	@NotEmpty
	@Schema(description = "내용", defaultValue = "내용")
	private String content;
	@Schema(description = "썸네일 이미지 URL", defaultValue = "https://teamten403.kr.object.ncloudstorage.com/tilog/fc89acb0-3aa4-4f3c-b821-6c6da62a175d.png")
	private String thumbnail;
	@NotNull
	@Schema(description = "태그 ID", defaultValue = "tagId")
	private String tagId;
}
