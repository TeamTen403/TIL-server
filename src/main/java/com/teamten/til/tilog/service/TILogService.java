package com.teamten.til.tilog.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.teamten.til.tilog.dto.TilogMonthly;
import com.teamten.til.tilog.repository.TilogRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TILogService {
	private final TilogRepository TILogRepository;

	public TilogMonthly getMontlyList(LocalDate ym) {

		return null;
	}
}
