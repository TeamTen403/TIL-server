package com.teamten.til.tilog.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.teamten.til.challenge.entity.Challenge;
import com.teamten.til.common.BaseTimeEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
public class Tag extends BaseTimeEntity {
	@Id
	private String id;
	private String name;

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tag", cascade = CascadeType.ALL)
	private List<Tilog> tilogList;

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tag", cascade = CascadeType.ALL)
	private List<Challenge> challenges;

}
