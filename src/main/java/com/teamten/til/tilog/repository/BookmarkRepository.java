package com.teamten.til.tilog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teamten.til.tiler.entity.TilerTemp;
import com.teamten.til.tilog.entity.Bookmark;
import com.teamten.til.tilog.entity.Tilog;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
	Optional<Bookmark> findByTilerAndTilog(TilerTemp tiler, Tilog tilog);
}
