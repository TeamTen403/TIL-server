package com.teamten.til.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.teamten.til.tilog.dto.FileUploadResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class StorageUploader {

	private final AmazonS3 amazonS3Client;

	@Value("${ncp.s3.bucket}")
	private String bucket;

	public FileUploadResponse upload(MultipartFile multipartFile, String dirName) throws IOException {

		File uploadFile = convertFile(multipartFile)
			.orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File로 전환이 실패했습니다."));

		String originFilename = uploadFile.getName();
		String fileExtention = originFilename.substring(originFilename.lastIndexOf("."), originFilename.length());
		String uploadFileName = UUID.randomUUID() + fileExtention;
		String fileName = dirName + "/" + uploadFileName;

		try {
			amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(
				CannedAccessControlList.PublicRead));
			String uploadImageUrl = amazonS3Client.getUrl(bucket, fileName).toString();
			return new FileUploadResponse(originFilename, uploadImageUrl);
		} catch (Exception e) {
			throw e;
		} finally {
			uploadFile.delete();
		}

	}

	private Optional<File> convertFile(MultipartFile file) throws IOException {
		File convertFile = new File(file.getOriginalFilename());
		if (convertFile.createNewFile()) {
			try (FileOutputStream fos = new FileOutputStream(convertFile)) {
				fos.write(file.getBytes());
			}
			return Optional.of(convertFile);
		}

		return Optional.empty();
	}

	//기존 Storage 파일 제거.
	public void remove(String imageUrl) {
		try {
			String storageUrl = "https://" + bucket + ".kr.object.ncloudstorage.com/";
			if (imageUrl.indexOf(storageUrl) > -1) {
				String objectName = imageUrl.replace(storageUrl, "");
				amazonS3Client.deleteObject(bucket, objectName);
			} else {
				log.info("ncp storage에 있는 파일이 아닙니다.");
			}

		} catch (AmazonS3Exception e) {
			e.printStackTrace();
		} catch (SdkClientException e) {
			e.printStackTrace();
		}
	}
}