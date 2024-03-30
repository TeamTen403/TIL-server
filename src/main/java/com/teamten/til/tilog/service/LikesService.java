package com.teamten.til.tilog.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.teamten.til.tiler.entity.Tiler;
import com.teamten.til.tilog.dto.LikeResponse;
import com.teamten.til.tilog.entity.Likes;
import com.teamten.til.tilog.entity.Tilog;
import com.teamten.til.tilog.repository.LikesRepository;
import com.teamten.til.tilog.repository.TilogRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LikesService {
	private final TilogRepository tilogRepository;
	private final LikesRepository likesRepository;

	@Transactional
	public LikeResponse addLikes(String email, Long tilogId) {
		Tiler searchTiler = Tiler.createById(email);
		Tilog searchTilog = Tilog.createById(tilogId);

		likesRepository.findByTilerAndTilog(searchTiler, searchTilog).ifPresent(likes -> {
			throw new RuntimeException("이미 좋아요를 눌렀습니다");
		});

		Likes like = Likes.builder().tilog(searchTilog).tiler(searchTiler).build();
		tilogRepository.incrementLikes(tilogId);
		likesRepository.save(like);

		Tilog tilog = tilogRepository.findById(tilogId).orElseThrow(() -> new RuntimeException("없는 id"));

		return LikeResponse.builder()
			.tilogId(tilogId)
			.likes(tilog.getLikes())
			.isLiked(true)
			.build();
	}

	@Transactional
	public LikeResponse removeLikes(String email, Long tilogId) {
		Tiler searchTiler = Tiler.createById(email);
		Tilog searchTilog = Tilog.createById(tilogId);

		Likes like = likesRepository.findByTilerAndTilog(searchTiler, searchTilog)
			.orElseThrow(() -> new RuntimeException("좋아요한 기록이 없습니다."));

		tilogRepository.decrementLikes(tilogId);
		likesRepository.deleteById(like.getId());

		Tilog tilog = tilogRepository.findById(tilogId).orElseThrow(() -> new RuntimeException("없는 id"));

		return LikeResponse.builder()
			.tilogId(tilogId)
			.likes(tilog.getLikes())
			.isLiked(false)
			.build();
	}
}
