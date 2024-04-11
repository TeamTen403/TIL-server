package com.teamten.til.tilog.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	public LikeResponse toggleLikes(LoginUser loginUser, Long tilogId) {
		Tiler tiler = loginUser.getUser();
		Tilog tilog = tilogRepository.findById(tilogId).orElseThrow(() -> new NotExistException());

		return likesRepository.findByTilerAndTilog(tiler, tilog)
			.map(likes -> {
				tilogRepository.decrementLikes(tilogId);
				likesRepository.deleteById(likes.getId());

				return LikeResponse.builder()
					.tilogId(tilogId)
					.likes(tilog.getLikes() - 1)
					.isLiked(false)
					.build();
			}).orElseGet(() -> {
				Likes likes = Likes.builder().tilog(tilog).tiler(tiler).build();

				tilogRepository.incrementLikes(tilogId);
				likesRepository.save(likes);

				return LikeResponse.builder()
					.tilogId(tilogId)
					.likes(tilog.getLikes() + 1)
					.isLiked(true)
					.build();
			});
	}

}
