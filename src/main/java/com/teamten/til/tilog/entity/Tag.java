package com.teamten.til.tilog.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Tag {
	@Id
	private String id;
}
