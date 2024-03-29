package com.teamten.til.tilog.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.teamten.til.tilog.dto.TagInfo;
import com.teamten.til.tilog.dto.TagInfoResponse;
import com.teamten.til.tilog.repository.TagRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TagService {
	private final TagRepository tagRepository;

	public TagInfoResponse getAll() {

		List<TagInfo> tagInfoList = tagRepository.findAll()
			.stream()
			.map(TagInfo::from)
			.collect(Collectors.toList());

		return TagInfoResponse.builder().tagList(tagInfoList).build();

	}
}
