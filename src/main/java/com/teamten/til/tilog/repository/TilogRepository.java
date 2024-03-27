package com.teamten.til.tilog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teamten.til.tilog.entity.Tilog;

public interface TilogRepository extends JpaRepository<Tilog, Long> {
}
