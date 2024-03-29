package com.teamten.til.tilog.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.teamten.til.tiler.entity.TilerTemp;
import com.teamten.til.tilog.dto.TilogInfo;
import com.teamten.til.tilog.dto.TilogMonthly;
import com.teamten.til.tilog.dto.TilogRequest;
import com.teamten.til.tilog.entity.Tag;
import com.teamten.til.tilog.entity.Tilog;
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
		TilerTemp tiler = findUser(email);

		List<TilogInfo> tilogList = tilogRepository.findAllByTilerAndRegYmdStartingWith(tiler, yyyyMM)
			.stream().map(tilog -> {
				boolean isLiked = likesRepository.findByTilerAndTilog(tiler, tilog).isPresent();
				boolean isBookmarked = bookmarkRepository.findByTilerAndTilog(tiler, tilog).isPresent();

				TilogInfo tilogInfo = TilogInfo.of(tilog, isLiked, isBookmarked);

				return tilogInfo;
			}).collect(Collectors.toList());

		return TilogMonthly.builder()
			.ym(yyyyMM)
			.tilogList(tilogList)
			.build();
	}

	public TilogInfo saveTilog(TilogRequest request, String email) {
		// TODO: 회원조회
		TilerTemp tiler = findUser(email);
		String yyyyMMdd = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

		// TODO: 오늘 이미 TILOG 작성했는지 체크
		tilogRepository.findByTilerAndRegYmd(tiler, yyyyMMdd).ifPresent(tilog -> {
			throw new RuntimeException("에러발생"); // TODO: 커스텀 예외로 변경
		});

		Tag tag = Tag.builder().id(request.getTagId()).build();

		Tilog tilog = Tilog.builder()
			.title(request.getTitle())
			.content(request.getContent())
			.tag(tag)
			.thumbnail(request.getThumbnail())
			.tiler(tiler)
			.build();

		tilogRepository.save(tilog);

		return TilogInfo.from(tilog);
	}

	public TilogInfo editTilog(Long tilogId, TilogRequest request, String email) {
		Tilog tilog = tilogRepository.findById(tilogId).orElseThrow(() -> new RuntimeException("없는 id"));

		// TODO: 회원조회

		if (!StringUtils.equals(tilog.getTiler().getEmail(), email)) {
			throw new RuntimeException("이메일이 다름");
		}

		tilog.editTilog(request);

		tilogRepository.save(tilog);

		return TilogInfo.from(tilog);
	}

	public void removeTilog(Long tilogId, String email) {
		Tilog tilog = tilogRepository.findById(tilogId).orElseThrow(() -> new RuntimeException("없는 id"));

		if (!StringUtils.equals(tilog.getTiler().getEmail(), email)) {
			throw new RuntimeException("이메일이 다름");
		}

		tilogRepository.deleteById(tilogId);
	}

	private TilerTemp findUser(String email) {
		return TilerTemp.createById(email);
	}

}
