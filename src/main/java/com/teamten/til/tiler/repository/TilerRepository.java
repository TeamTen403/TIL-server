package com.teamten.til.tiler.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teamten.til.tiler.entity.Tiler;

public interface TilerRepository extends JpaRepository<Tiler, UUID> {

	Optional<Tiler> findByEmail(String email);
}
