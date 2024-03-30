package com.teamten.til.tilog.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.teamten.til.tiler.entity.Tiler;
import com.teamten.til.tilog.dto.BookmarkResponse;
import com.teamten.til.tilog.dto.TilogInfo;
import com.teamten.til.tilog.dto.TilogInfoResponse;
import com.teamten.til.tilog.entity.Bookmark;
import com.teamten.til.tilog.entity.Tilog;
import com.teamten.til.tilog.repository.BookmarkRepository;
import com.teamten.til.tilog.repository.LikesRepository;
import com.teamten.til.tilog.repository.TilogRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BookmarkService {
	private final TilogRepository tilogRepository;
	private final BookmarkRepository bookmarkRepository;
	private final LikesRepository likesRepository;

	public BookmarkResponse addBookmark(String tilerId, Long tilogId) {
		Tiler searchTiler = Tiler.createById(tilerId);

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

		Tiler tiler = Tiler.createById(tilerId);
		Tilog tilog = Tilog.createById(tilogId);

		Bookmark bookmark = bookmarkRepository.findByTilerAndTilog(tiler, tilog)
			.orElseThrow(() -> new RuntimeException("북마크 내역이 없습니다."));

		bookmarkRepository.deleteById(bookmark.getId());

		return BookmarkResponse.builder()
			.tilogId(tilogId)
			.isBookmarked(false)
			.build();
	}

	@Transactional(readOnly = true)
	public TilogInfoResponse getAllMyBookmark(String tilerId) {
		Tiler tiler = Tiler.createById(tilerId);

		List<TilogInfo> tilogInfoList = tiler.getBookmarkList()
			.stream()
			.map(Bookmark::getTilog)
			.map(tilog -> {
				boolean isLiked = likesRepository.findByTilerAndTilog(tiler, tilog).isPresent();
				return TilogInfo.of(tilog, isLiked, true);
			}).collect(Collectors.toList());

		return TilogInfoResponse.builder().tilogList(tilogInfoList).build();
	}
}
