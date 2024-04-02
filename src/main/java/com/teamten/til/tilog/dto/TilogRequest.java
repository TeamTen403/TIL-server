package com.teamten.til.tilog.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class TilogRequest {
	@NotEmpty
	private String title;
	@NotEmpty
	private String content;
	private String thumbnail;
	@NotNull
	private String tagId;
}
