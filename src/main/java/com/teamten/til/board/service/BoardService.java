package com.teamten.til.board.service;

import org.springframework.stereotype.Service;

import com.teamten.til.board.repository.BoardRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardService {
	private final BoardRepository boardRepository;
}
