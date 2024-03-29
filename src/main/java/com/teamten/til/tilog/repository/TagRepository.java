package com.teamten.til.tilog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teamten.til.tilog.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
