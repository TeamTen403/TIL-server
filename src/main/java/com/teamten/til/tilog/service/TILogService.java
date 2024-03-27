package com.teamten.til.tilog.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.teamten.til.tilog.dto.TilogMonthly;
import com.teamten.til.tilog.repository.TILogRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TILogService {
	private final TILogRepository TILogRepository;

	public TilogMonthly getMontlyList(LocalDate ym) {

		return null;
	}
}
