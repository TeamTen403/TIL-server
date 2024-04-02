package com.teamten.til.tilog.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.teamten.til.common.exception.DuplicatedException;
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

	@Transactional
	public BookmarkResponse addBookmark(String tilerId, Long tilogId) {
		Tiler searchTiler = Tiler.createById(tilerId);

		Tilog tilog = tilogRepository.findById(tilogId)
			.orElseThrow(() -> new NoSuchElementException("존재하지 않는 tilog입니다."));

		bookmarkRepository.findByTilerAndTilog(searchTiler, tilog).ifPresent(likes -> {
			throw new DuplicatedException();
		});

		Bookmark bookmark = Bookmark.builder().tiler(searchTiler).tilog(tilog).build();
		bookmarkRepository.save(bookmark);

		return BookmarkResponse.builder()
			.tilogId(tilogId)
			.isBookmarked(true)
			.build();
	}

	@Transactional
	public BookmarkResponse removeBookmark(String tilerId, Long tilogId) {

		Tiler tiler = Tiler.createById(tilerId);
		Tilog tilog = Tilog.createById(tilogId);

		Bookmark bookmark = bookmarkRepository.findByTilerAndTilog(tiler, tilog)
			.orElseThrow(() -> new DuplicatedException());

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
