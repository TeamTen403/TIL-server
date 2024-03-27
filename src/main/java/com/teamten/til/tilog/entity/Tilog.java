package com.teamten.til.tilog.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.teamten.til.tiler.entity.TIler;

import jakarta.persistence.CascadeType;
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

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Tilog {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JoinColumn(name = "email", nullable = false)
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private TIler user;
	private String title;
	private String content;
	private String thumbnail;
	private String regYm;
	@JoinColumn(name = "tag_id")
	@ManyToOne(fetch = FetchType.EAGER)
	private Tag tag;
	@Builder.Default
	private long likes = 0;
	@CreatedDate
	private LocalDateTime regYmdt;

	@LastModifiedDate
	private LocalDateTime modYmdt;

	public String getTagName() {
		if (Objects.isNull(tag)) {
			return null;
		}

		return tag.getName();
	}

}
