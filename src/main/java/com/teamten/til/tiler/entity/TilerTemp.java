package com.teamten.til.tiler.entity;

import java.util.List;

import com.teamten.til.tilog.entity.Bookmark;
import com.teamten.til.tilog.entity.Tilog;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tiler_temp")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TilerTemp {
	public static TilerTemp createById(String email) {
		return TilerTemp.builder().email(email).build();
	}

	@Id
	private String email;
	private String nickName;
	private String passwd; // 암호화해서 저장
	private String job; // 직무정보

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tiler", cascade = CascadeType.ALL)
	private List<Tilog> tilogList;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tiler", cascade = CascadeType.ALL)
	private List<Bookmark> bookmarkList;
}
