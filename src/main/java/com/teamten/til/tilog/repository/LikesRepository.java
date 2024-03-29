package com.teamten.til.tilog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teamten.til.tiler.entity.TilerTemp;
import com.teamten.til.tilog.entity.Likes;
import com.teamten.til.tilog.entity.Tilog;

public interface LikesRepository extends JpaRepository<Likes, Long> {
	Optional<Likes> findByTilerAndTilog(TilerTemp tiler, Tilog tilog);
}
