package com.teamten.til.tilog.dto;

import lombok.Getter;

@Getter
public class TilogRequest {
	private String title;
	private String content;
	private String thumbnail;
	private Long tagId;
}
