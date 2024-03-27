package com.teamten.til.tilog.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.teamten.til.tilog.dto.TilogInfo;
import com.teamten.til.tilog.dto.TilogMonthly;
import com.teamten.til.tilog.entity.key.TilogTilerCompositeKey;
import com.teamten.til.tilog.repository.BookmarkRepository;
import com.teamten.til.tilog.repository.LikesRepository;
import com.teamten.til.tilog.repository.TilogRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TilogService {
	private final TilogRepository tilogRepository;
	private final LikesRepository likesRepository;
	private final BookmarkRepository bookmarkRepository;

	public TilogMonthly getMontlyList(LocalDate ym, String email) {
		String yyyyMM = ym.format(DateTimeFormatter.ofPattern("yyyyMM"));

		List<TilogInfo> tilogList = tilogRepository.findAllByEmailAndRegYm(email, yyyyMM)
			.stream().map(tilog -> {
				TilogInfo tilogInfo = TilogInfo.from(tilog);

				TilogTilerCompositeKey compositeKey = new TilogTilerCompositeKey(tilog.getId(), email);

				boolean isLiked = likesRepository.findById(compositeKey).isPresent();
				boolean isBookmarked = bookmarkRepository.findById(compositeKey).isPresent();

				tilogInfo.setIsLiked(isLiked);
				tilogInfo.setIsBookmarked(isBookmarked);

				return tilogInfo;
			}).collect(Collectors.toList());

		return TilogMonthly.builder()
			.ym(yyyyMM)
			.tilogList(tilogList)
			.build();
	}

}
