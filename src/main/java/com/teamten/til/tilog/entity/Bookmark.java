package com.teamten.til.tilog.entity;

import com.teamten.til.common.BaseTimeEntity;
import com.teamten.til.tiler.entity.Tiler;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Bookmark extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tiler_id", unique = true)
	private Tiler tiler;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tilog_id", unique = true)
	private Tilog tilog;
}