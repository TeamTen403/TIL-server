package com.teamten.til.tiler.repository;

import com.teamten.til.tiler.entity.Tiler;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Tiler, String> {
    //Optional<Tiler> findByNickName(String nickName);

    Optional<Tiler> findByEmail(String email);
}
