package com.teamten.til.tilog.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.teamten.til.common.exception.NotExistException;
import com.teamten.til.tiler.entity.LoginUser;
import com.teamten.til.tiler.entity.Tiler;
import com.teamten.til.tiler.repository.TilerRepository;
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
	private final TilerRepository tilerRepository;
	private final BookmarkRepository bookmarkRepository;
	private final LikesRepository likesRepository;

	@Transactional
	public BookmarkResponse toggleBookmark(LoginUser loginUser, Long tilogId) {
		Tiler tiler = loginUser.getUser();

		Tilog tilog = tilogRepository.findById(tilogId)
			.orElseThrow(() -> new NotExistException());

		return bookmarkRepository.findByTilerAndTilog(tiler, tilog)
			.map(bookmark -> {
				bookmarkRepository.deleteById(bookmark.getId());

				return BookmarkResponse.builder()
					.tilogId(tilogId)
					.isBookmarked(false)
					.build();
			})
			.orElseGet(() -> {
				Bookmark bookmark = Bookmark.builder().tiler(tiler).tilog(tilog).build();

				bookmarkRepository.save(bookmark);

				return BookmarkResponse.builder()
					.tilogId(tilogId)
					.isBookmarked(true)
					.build();
			});
	}

	@Transactional(readOnly = true)
	public TilogInfoResponse getAllMyBookmark(LoginUser loginUser) {
		Tiler tiler = tilerRepository.findById(loginUser.getId()).orElseThrow(() -> new NotExistException());

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
