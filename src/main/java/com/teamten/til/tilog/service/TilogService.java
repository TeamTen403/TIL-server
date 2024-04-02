package com.teamten.til.tilog.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.teamten.til.common.exception.DuplicatedException;
import com.teamten.til.common.exception.InvalidException;
import com.teamten.til.tiler.entity.Tiler;
import com.teamten.til.tilog.dto.FeedResponse;
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

	@Transactional(readOnly = true)
	public TilogMonthly getMontlyList(LocalDate ym, String tilerId) {
		String yyyyMM = ym.format(DateTimeFormatter.ofPattern("yyyyMM"));
		Tiler tiler = findUser(tilerId);

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

	@Transactional
	public TilogInfo saveTilog(TilogRequest request, String tilerId) {
		// TODO: 회원조회
		Tiler tiler = findUser(tilerId);
		String yyyyMMdd = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

		// TODO: 오늘 이미 TILOG 작성했는지 체크
		tilogRepository.findByTilerAndRegYmd(tiler, yyyyMMdd).ifPresent(tilog -> {
			throw new DuplicatedException();
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

	@Transactional
	public TilogInfo editTilog(Long tilogId, TilogRequest request, String tilerId) {
		Tilog tilog = tilogRepository.findById(tilogId).orElseThrow(() -> new RuntimeException("없는 id"));

		// TODO: 회원조회

		if (!StringUtils.equals(tilog.getTiler().getId().toString(), tilerId)) {
			throw new InvalidException();
		}

		tilog.editTilog(request);

		tilogRepository.save(tilog);

		return TilogInfo.from(tilog);
	}

	@Transactional
	public void removeTilog(Long tilogId, String tilerId) {
		Tilog tilog = tilogRepository.findById(tilogId).orElseThrow(() -> new RuntimeException("존재하지 않는 tilog입니다."));

		if (!StringUtils.equals(tilog.getTiler().getId().toString(), tilerId)) {
			throw new InvalidException();
		}

		tilogRepository.deleteById(tilogId);
	}

	@Transactional(readOnly = true)
	public FeedResponse getFeed(String tilerId) {
		Tiler tiler = Tiler.createById(tilerId);

		// TODO: 페이지네이션 도입되면 수정필요
		List<TilogInfo> allTilerInfo = tilogRepository.findAllByOrderByRegYmdDescRegYmdtDesc()
			.stream().map(tilog -> {
				boolean isLiked = likesRepository.findByTilerAndTilog(tiler, tilog).isPresent();
				boolean isBookmarked = bookmarkRepository.findByTilerAndTilog(tiler, tilog).isPresent();

				TilogInfo tilogInfo = TilogInfo.of(tilog, isLiked, isBookmarked);

				return tilogInfo;
			}).collect(Collectors.toList());

		// TODO: 인기글은 캐싱 필요
		List<TilogInfo> popularList = allTilerInfo.stream()
			.filter(tilogInfo -> {
				LocalDateTime regYmdt = tilogInfo.getRegYmdt(); // regYmdt를 가져온다
				return regYmdt != null && regYmdt.isAfter(LocalDateTime.now().minusDays(2)); // 최신 2일 이내의 글 필터링
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

	private Tiler findUser(String tilerId) {
		return Tiler.createById(tilerId);
	}

}
