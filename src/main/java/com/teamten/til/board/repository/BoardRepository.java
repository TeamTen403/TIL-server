package com.teamten.til.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teamten.til.board.entity.Board;

public interface BoardRepository extends JpaRepository<Board, String> {
}
