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
	private String nickName;
	private String profileImage;
	private String job;
	private AuthProvider authProvider = AuthProvider.TIL_EMAIL;

}
