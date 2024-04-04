package com.teamten.til.tiler.entity;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.teamten.til.challenge.entity.ChallengeParticipant;
import com.teamten.til.common.BaseTimeEntity;
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
import jakarta.persistence.ManyToOne;
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
public class Tiler extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(columnDefinition = "BINARY(16)")
	private UUID id;
	@Column(unique = true)
	private String email;
	private String nickname;
	private String profileImage;
	private String passwd; // 암호화해서 저장
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Job job; // 직무정보

	@JsonIgnore
	@Enumerated(EnumType.STRING)
	private AuthProvider authProvider; // 로그인정보

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tiler", cascade = CascadeType.ALL)
	private List<Tilog> tilogList;

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tiler", cascade = CascadeType.ALL)
	private List<Bookmark> bookmarkList;

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tiler", cascade = CascadeType.ALL)
	private List<ChallengeParticipant> challengeParticipants;

}