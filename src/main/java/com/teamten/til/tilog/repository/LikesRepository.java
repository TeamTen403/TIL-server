package com.teamten.til.tilog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teamten.til.tilog.entity.Likes;
import com.teamten.til.tilog.entity.key.TilogTilerCompositeKey;

public interface LikesRepository extends JpaRepository<Likes, TilogTilerCompositeKey> {
}
