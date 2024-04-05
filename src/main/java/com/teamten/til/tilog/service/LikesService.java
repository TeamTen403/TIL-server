package com.teamten.til.tilog.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.teamten.til.common.exception.DuplicatedException;
import com.teamten.til.common.exception.NotExistException;
import com.teamten.til.tiler.entity.LoginUser;
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
	public LikeResponse addLikes(LoginUser loginUser, Long tilogId) {
		Tiler tiler = loginUser.getUser();
		Tilog tilog = tilogRepository.findById(tilogId).orElseThrow(() -> new NotExistException());

		likesRepository.findByTilerAndTilog(tiler, tilog).ifPresent(likes -> {
			throw new DuplicatedException("이미 좋아요를 눌렀습니다");
		});

		Likes like = Likes.builder().tilog(tilog).tiler(tiler).build();
		tilogRepository.incrementLikes(tilogId);
		likesRepository.save(like);

		return LikeResponse.builder()
			.tilogId(tilogId)
			.likes(tilog.getLikes() + 1)
			.isLiked(true)
			.build();
	}

	@Transactional
	public LikeResponse removeLikes(LoginUser loginUser, Long tilogId) {
		Tiler tiler = loginUser.getUser();
		Tilog tilog = tilogRepository.findById(tilogId).orElseThrow(() -> new NotExistException());

		Likes like = likesRepository.findByTilerAndTilog(tiler, tilog)
			.orElseThrow(() -> new DuplicatedException("좋아요한 기록이 없습니다."));

		tilogRepository.decrementLikes(tilogId);
		likesRepository.deleteById(like.getId());

		return LikeResponse.builder()
			.tilogId(tilogId)
			.likes(tilog.getLikes() - 1)
			.isLiked(false)
			.build();
	}
}
