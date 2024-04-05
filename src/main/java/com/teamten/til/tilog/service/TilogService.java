package com.teamten.til.tilog.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.teamten.til.common.exception.DuplicatedException;
import com.teamten.til.common.exception.InvalidException;
import com.teamten.til.common.exception.NotExistException;
import com.teamten.til.tiler.entity.LoginUser;
import com.teamten.til.tiler.entity.Tiler;
import com.teamten.til.tilog.dto.FeedResponse;
import com.teamten.til.tilog.dto.TilogInfo;
import com.teamten.til.tilog.dto.TilogMonthly;
import com.teamten.til.tilog.dto.TilogRequest;
import com.teamten.til.tilog.entity.Tag;
import com.teamten.til.tilog.entity.Tilog;
import com.teamten.til.tilog.repository.BookmarkRepository;
import com.teamten.til.tilog.repository.LikesRepository;
import com.teamten.til.tilog.repository.TagRepository;
import com.teamten.til.tilog.repository.TilogRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TilogService {
	private final TilogRepository tilogRepository;
	private final LikesRepository likesRepository;
	private final BookmarkRepository bookmarkRepository;
	private final TagRepository tagRepository;

	@Transactional(readOnly = true)
	public TilogInfo getById(LoginUser loginUser, Long tilogId) {
		Tilog tilog = tilogRepository.findById(tilogId).orElseThrow(() -> new NotExistException());

		return getTilogInfo(loginUser, tilog);
	}

	@Transactional(readOnly = true)
	public TilogMonthly getMontlyList(LoginUser loginUser, LocalDate ym) {
		String yyyyMM = ym.format(DateTimeFormatter.ofPattern("yyyyMM"));
		Tiler tiler = loginUser.getUser();

		List<TilogInfo> tilogList = tilogRepository.findAllByTilerAndRegYmdStartingWith(tiler, yyyyMM)
			.stream().map(tilog -> getTilogInfo(loginUser, tilog))
			.collect(Collectors.toList());

		return TilogMonthly.builder()
			.ym(yyyyMM)
			.tilogList(tilogList)
			.build();
	}

	@Transactional
	public TilogInfo saveTilog(LoginUser loginUser, TilogRequest request) {
		String yyyyMMdd = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

		Tiler tiler = loginUser.getUser();
		tilogRepository.findByTilerAndRegYmd(tiler, yyyyMMdd).ifPresent(tilog -> {
			throw new DuplicatedException();
		});

		Tag tag = tagRepository.findById(request.getTagId()).orElseThrow(() -> new NotExistException());

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

	@Transactional
	public TilogInfo editTilog(LoginUser loginUser, Long tilogId, TilogRequest request) {
		Tilog tilog = tilogRepository.findById(tilogId).orElseThrow(() -> new NotExistException());
		Tiler tiler = loginUser.getUser();

		if (!StringUtils.equals(tilog.getTiler().getId().toString(), tiler.getId().toString())) {
			throw new InvalidException();
		}

		tilog.editTilog(request);

		tilogRepository.save(tilog);

		return TilogInfo.from(tilog);
	}

	@Transactional
	public void removeTilog(LoginUser loginUser, Long tilogId) {
		Tilog tilog = tilogRepository.findById(tilogId).orElseThrow(() -> new NotExistException());
		Tiler tiler = loginUser.getUser();

		if (!StringUtils.equals(tilog.getTiler().getId().toString(), tiler.getId().toString())) {
			throw new InvalidException();
		}

		tilogRepository.deleteById(tilogId);
	}

	@Transactional(readOnly = true)
	public FeedResponse getFeed(LoginUser loginUser) {

		// TODO: 페이지네이션 도입되면 수정필요
		List<TilogInfo> allTilerInfo = tilogRepository.findAllByOrderByRegYmdDescRegYmdtDesc()
			.stream().map(tilog -> getTilogInfo(loginUser, tilog))
			.collect(Collectors.toList());

		// TODO: 인기글은 캐싱 필요
		List<TilogInfo> popularList = allTilerInfo.stream()
			.filter(tilogInfo -> {
				LocalDateTime regYmdt = tilogInfo.getRegYmdt(); // regYmdt를 가져온다
				return regYmdt.isAfter(LocalDateTime.now().minusDays(2)); // 최신 2일 이내의 글 필터링
			})
			.sorted(Comparator.comparingLong(TilogInfo::getLikeCount).reversed()) // likes value 내림차순으로 정렬
			.limit(5) // 상위 5개만 선택
			.collect(Collectors.toList());

		List<TilogInfo> tilogInfoList = allTilerInfo.stream()
			.filter(tilogInfo -> !popularList.contains(tilogInfo))
			.collect(Collectors.toList());

		return FeedResponse.builder()
			.popularList(popularList)
			.tilogList(tilogInfoList)
			.build();
	}

	private TilogInfo getTilogInfo(LoginUser loginUser, Tilog tilog) {
		if (!Objects.isNull(loginUser)) {
			boolean isLiked = likesRepository.findByTilerAndTilog(loginUser.getUser(), tilog).isPresent();
			boolean isBookmarked = bookmarkRepository.findByTilerAndTilog(loginUser.getUser(), tilog).isPresent();

			return TilogInfo.of(tilog, isLiked, isBookmarked);
		}

		return TilogInfo.from(tilog);
	}

}
