package com.teamten.til.tilog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadResponse {
	@Schema(description = "이미지 파일명", defaultValue = "파일명")
	String fileName;
	@Schema(description = "이미지 URL", defaultValue = "https://teamten403.kr.object.ncloudstorage.com/tilog/fc89acb0-3aa4-4f3c-b821-6c6da62a175d.png")
	String url;
}
