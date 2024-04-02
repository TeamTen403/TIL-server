package com.teamten.til.tilog.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import com.teamten.til.common.BaseTimeEntity;
import com.teamten.til.tiler.entity.Tiler;
import com.teamten.til.tilog.dto.TilogRequest;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Tilog extends BaseTimeEntity {
	public static Tilog createById(Long id) {
		return Tilog.builder().id(id).build();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JoinColumn(name = "tiler_id", nullable = false)
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Tiler tiler;
	private String title;
	@Column(columnDefinition = "TEXT")
	private String content;
	private String thumbnail;
	private String regYmd;
	@JoinColumn(name = "tag_id")
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Tag tag;
	@Builder.Default
	private long likes = 0;

	public String getTagName() {
		if (Objects.isNull(tag)) {
			return null;
		}

		return tag.getName();
	}

	@PrePersist
	public void onPrePersist() {
		this.regYmd = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
	}

	public void editTilog(TilogRequest request) {
		this.title = request.getTitle();
		this.content = request.getContent();
		this.thumbnail = request.getThumbnail();
		this.tag = Tag.builder()
			.id(request.getTagId())
			.build();
	}
}
