package com.teamten.til.tiler.entity;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.teamten.til.challenge.entity.ChallengeParticipant;
import com.teamten.til.tilog.entity.Bookmark;
import com.teamten.til.tilog.entity.Tilog;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
	public static Tiler createById(String tilerId) {
		return Tiler.builder().id(UUID.fromString(tilerId)).build();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(columnDefinition = "BINARY(16)")
	private UUID id;
	@Column(unique = true)
	private String email;
	private String nickName;
	private String passwd; // 암호화해서 저장
	private String job; // 직무정보

	@JsonIgnore
	@Enumerated(EnumType.STRING)
	private AuthProvider authProvider; // 로그인정보

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tiler", cascade = CascadeType.ALL)
	private List<Tilog> tilogList;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tiler", cascade = CascadeType.ALL)
	private List<Bookmark> bookmarkList;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tiler", cascade = CascadeType.ALL)
	private List<ChallengeParticipant> challengeParticipants;

}