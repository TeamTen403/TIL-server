package com.teamten.til.tilog.service;

import org.springframework.stereotype.Service;

import com.teamten.til.tiler.entity.TilerTemp;
import com.teamten.til.tilog.dto.BookmarkResponse;
import com.teamten.til.tilog.entity.Bookmark;
import com.teamten.til.tilog.entity.Tilog;
import com.teamten.til.tilog.repository.BookmarkRepository;
import com.teamten.til.tilog.repository.TilogRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BookmarkService {
	private final TilogRepository tilogRepository;
	private final BookmarkRepository bookmarkRepository;

	public BookmarkResponse addBookmark(String tilerId, Long tilogId) {
		TilerTemp searchTiler = TilerTemp.createById(tilerId);

		Tilog tilog = tilogRepository.findById(tilogId).orElseThrow(() -> new RuntimeException("없는 id"));

		bookmarkRepository.findByTilerAndTilog(searchTiler, tilog).ifPresent(likes -> {
			throw new RuntimeException("이미 북마크를 저장했습니다.");
		});

		Bookmark bookmark = Bookmark.builder().tiler(searchTiler).tilog(tilog).build();
		bookmarkRepository.save(bookmark);

		return BookmarkResponse.builder()
			.tilogId(tilogId)
			.isBookmarked(true)
			.build();
	}

	public BookmarkResponse removeBookmark(String tilerId, Long tilogId) {

		TilerTemp tiler = TilerTemp.createById(tilerId);
		Tilog tilog = Tilog.createById(tilogId);

		Bookmark bookmark = bookmarkRepository.findByTilerAndTilog(tiler, tilog)
			.orElseThrow(() -> new RuntimeException("북마크 내역이 없습니다."));

		bookmarkRepository.deleteById(bookmark.getId());

		return BookmarkResponse.builder()
			.tilogId(tilogId)
			.isBookmarked(false)
			.build();
	}
}
