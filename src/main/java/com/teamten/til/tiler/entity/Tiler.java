package com.teamten.til.tiler.entity;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Entity
@Table(name = "tiler")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class Tiler {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(columnDefinition = "BINARY(16)")
	private UUID id;
	private String email;
	private String nickName;
	private String passwd; // 암호화해서 저장
	private String job; // 직무정보

	@JsonIgnore
	@Enumerated(EnumType.STRING)
	private AuthProvider authProvider; // 로그인정보

}