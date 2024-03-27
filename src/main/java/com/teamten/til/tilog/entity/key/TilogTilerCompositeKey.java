package com.teamten.til.tilog.entity.key;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Embeddable
@EqualsAndHashCode
public class TilogTilerCompositeKey implements Serializable {
	@Column(name = "tilog_id")
	private Long tilogId;

	@Column(name = "tiler_email")
	private String tilerEmail;
}
