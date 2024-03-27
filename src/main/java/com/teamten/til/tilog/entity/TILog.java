package com.teamten.til.tilog.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tilog")
public class TILog {
	@Id
	private String id;



}
