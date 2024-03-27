package com.teamten.til.tilog.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.teamten.til.tilog.dto.TilogInfo;
import com.teamten.til.tilog.dto.TilogMonthly;
import com.teamten.til.tilog.dto.TilogRequest;
import com.teamten.til.tilog.entity.Tag;
import com.teamten.til.tilog.entity.Tilog;
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

		List<TilogInfo> tilogList = tilogRepository.findAllByEmailAndRegYmdStartingWith(email, yyyyMM)
			.stream().map(tilog -> {
				TilogTilerCompositeKey compositeKey = new TilogTilerCompositeKey(tilog.getId(), email);

				boolean isLiked = likesRepository.findById(compositeKey).isPresent();
				boolean isBookmarked = bookmarkRepository.findById(compositeKey).isPresent();

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

		String yyyyMMdd = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

		// TODO: 오늘 이미 TILOG 작성했는지 체크
		tilogRepository.findByEmailAndRegYmd(email, yyyyMMdd).ifPresent(tilog -> {
			throw new RuntimeException("에러발생"); // TODO: 커스텀 예외로 변경
		});

		Tag tag = Tag.builder().id(request.getTagId()).build();

		Tilog tilog = Tilog.builder()
			.title(request.getTitle())
			.content(request.getContent())
			.tag(tag)
			.thumbnail(request.getThumbnail())
			// .user(new TIler)
			.build();

		tilogRepository.save(tilog);

		return TilogInfo.from(tilog);
	}

	public TilogInfo editTilog(Long tilogId, TilogRequest request, String email) {
		Tilog tilog = tilogRepository.findById(tilogId).orElseThrow(() -> new RuntimeException("없는 id"));

		// TODO: 회원조회

		if (!StringUtils.equals(tilog.getUser().getEmail(), email)) {
			throw new RuntimeException("이메일이 다름");
		}

		tilog.editTilog(request);

		tilogRepository.save(tilog);

		return TilogInfo.from(tilog);
	}

	public void removeTilog(Long tilogId, String email) {
		Tilog tilog = tilogRepository.findById(tilogId).orElseThrow(() -> new RuntimeException("없는 id"));

		if (!StringUtils.equals(tilog.getUser().getEmail(), email)) {
			throw new RuntimeException("이메일이 다름");
		}

		tilogRepository.deleteById(tilogId);
	}

}
