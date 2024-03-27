package com.teamten.til.tilog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teamten.til.tilog.entity.Bookmark;

public interface BookmarkRepository extends JpaRepository<Bookmark, String> {
}
