package com.teamten.til.tilog.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teamten.til.tilog.entity.Tilog;

public interface TilogRepository extends JpaRepository<Tilog, Long> {
	List<Tilog> findAllByEmailAndRegYmdStartingWith(String email, String regYm);

	Optional<Tilog> findByEmailAndRegYmd(String email, String regYmd);
}
