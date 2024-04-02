package com.teamten.til.tilog.dto;

import java.io.Serializable;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TilogMonthly implements Serializable {
	@Schema(description = "조회 연월", example = "yyyyMM")
	private String ym;
	private List<TilogInfo> tilogList;

}