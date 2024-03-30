package com.teamten.til.tiler.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Job {
	@Id
	private String id;

	private String name;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "job", cascade = CascadeType.ALL)
	private List<Tiler> tilerList;
}
