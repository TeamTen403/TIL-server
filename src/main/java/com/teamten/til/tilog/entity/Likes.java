package com.teamten.til.tilog.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import com.teamten.til.tilog.entity.key.TilogTilerCompositeKey;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Likes {
	@EmbeddedId
	private TilogTilerCompositeKey id;
	@CreatedDate
	private LocalDateTime regYmdt;
}
