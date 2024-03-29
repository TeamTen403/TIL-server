package com.teamten.til.tiler.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tiler")
@Getter
@NoArgsConstructor
public class TIler {
	@Id
	private String email;
	private String nickName;
	private String passwd; // 암호화해서 저장
	private String job; // 직무정보
	@NotNull
	@JsonIgnore
	@Enumerated(EnumType.STRING)
	private AuthProvider authProvider; // 로그인정보

	public TIler(String email) { //TODO: 지워주셔요
		this.email = email;
	}
}
