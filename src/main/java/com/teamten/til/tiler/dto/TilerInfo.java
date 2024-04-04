package com.teamten.til.tiler.dto;

import java.util.UUID;

import com.teamten.til.tiler.entity.AuthProvider;
import com.teamten.til.tiler.entity.Tiler;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TilerInfo {
	private UUID tilerId;
	private String email;
	private String profileImage;
	private String nickname;
	private String jobId;
	private String jobName;
	private String regYmdt;
	private String modYmdt;
	private AuthProvider authProvider;

	public static TilerInfo from(Tiler tiler) {
		return TilerInfo.builder()
			.tilerId(tiler.getId())
			.email(tiler.getEmail())
			.profileImage(tiler.getProfileImage())
			.nickname(tiler.getNickname())
			.authProvider(tiler.getAuthProvider())
			.jobName(tiler.getJob().getName())
			.jobId(tiler.getJob().getId())
			.build();
	}
}
