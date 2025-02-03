package com.profitkey.stock.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.profitkey.stock.entity.UploadFile;
import com.profitkey.stock.repository.UploadFileRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3UploadService {
	private final AmazonS3Client amazonS3Client;
	private final UploadFileRepository uploadFileRepository;

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	public UploadFile uploadFile(MultipartFile file) throws IOException {
		String originalFilename = file.getOriginalFilename();
		String fileExtension = getFileExtension(originalFilename);
		String uniqueFileName = generateUniqueFileName(fileExtension);

		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentType(file.getContentType());
		metadata.setContentLength(file.getSize());

		amazonS3Client.putObject(new PutObjectRequest(bucket, uniqueFileName, file.getInputStream(), metadata));

		String fileUrl = amazonS3Client.getUrl(bucket, uniqueFileName).toString();

		// DB에 파일 정보 저장
		UploadFile uploadFile = dbSaveFile(originalFilename, uniqueFileName);

		return uploadFile;
	}

	private UploadFile dbSaveFile(String fileName, String fileKey) {
		try {
			UploadFile uploadFile = UploadFile.builder().fileName(fileName).fileKey(fileKey).build();

			UploadFile uploadFileInfo = uploadFileRepository.save(uploadFile);
			log.info("File info saved to DB - fileName: {}, fileKey: {}", fileName, fileKey);
			return uploadFileInfo;
		} catch (Exception e) {
			log.error("Failed to save file info to DB - fileName: {}, fileKey: {}", fileName, fileKey, e);
			throw new RuntimeException("Failed to save file information to database", e);
		}
	}

	private String generateUniqueFileName(String fileExtension) {
		return UUID.randomUUID() + fileExtension;
	}

	private String getFileExtension(String fileName) {
		if (fileName == null)
			return "";
		int lastDotIndex = fileName.lastIndexOf(".");
		return (lastDotIndex == -1) ? "" : fileName.substring(lastDotIndex);
	}
} 