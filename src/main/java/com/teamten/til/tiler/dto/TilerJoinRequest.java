package com.teamten.til.tiler.dto;

import com.teamten.til.tiler.entity.AuthProvider;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class TilerJoinRequest {
	private String email;
	private String passwd;
	private String nickname;
	private String profileImage;
	private String jobId;
	private AuthProvider authProvider = AuthProvider.TIL_EMAIL;

}
