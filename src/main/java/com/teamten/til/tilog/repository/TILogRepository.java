package com.teamten.til.tilog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teamten.til.tilog.entity.TILog;

public interface TILogRepository extends JpaRepository<TILog, String> {
}
