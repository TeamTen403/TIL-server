package com.teamten.til.tilog.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TagInfoResponse {
	private List<TagInfo> tagList;
}
