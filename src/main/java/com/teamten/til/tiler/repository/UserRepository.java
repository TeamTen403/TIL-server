package com.teamten.til.tiler.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teamten.til.tiler.entity.Tiler;

public interface UserRepository extends JpaRepository<Tiler, UUID> {
	//Optional<Tiler> findByNickName(String nickName);

	Optional<Tiler> findByEmail(String email);
}
