package com.profitkey.stock.service.faq;

import com.profitkey.stock.dto.common.FileInfo;
import com.profitkey.stock.dto.response.faq.FaqCreateResponse;
import com.profitkey.stock.entity.Faq;
import com.profitkey.stock.entity.FaqCategoryCode;
import com.profitkey.stock.entity.UploadFile;
import com.profitkey.stock.repository.UploadFileRepository;
import com.profitkey.stock.repository.faq.FaqRepository;
import com.profitkey.stock.repository.faq.FaqCategoryRepository;
import com.profitkey.stock.service.S3UploadService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FaqService {
	@Autowired
	private final S3UploadService s3UploadService;
	private final FaqRepository faqRepository;
	private final UploadFileRepository uploadFileRepository;
	private final FaqCategoryRepository faqCategoryRepository;

	@Transactional
	public FaqCreateResponse createFaq(MultipartFile[] files, Boolean published, String faqCategoryName, String title,
		String content) {
		List<UploadFile> uploadedFiles = new ArrayList<>();
		try {
			// 카테고리 조회
			FaqCategoryCode category = faqCategoryRepository.findByCategoryName(faqCategoryName)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
					"카테고리를 찾지 못했습니다. 카테고리: " + faqCategoryName));

			// FAQ 생성
			Faq faq = Faq.builder().title(title).content(content).published(published).faqCategory(category).build();

			faqRepository.save(faq);

			// 파일이 있는 경우 처리
			if (files != null && files.length > 0) {
				// S3에 파일들 업로드 및 DB에 저장
				uploadedFiles = s3UploadService.uploadFiles(files);

				// 이미 저장된 UploadFile들에 FAQ 연관관계 설정
				for (UploadFile uploadFile : uploadedFiles) {
					uploadFile.setFaq(faq);
				}
			}

			FileInfo[] fileInfos = uploadedFiles.stream()
				.map(file -> new FileInfo(file.getFileName(), file.getFileKey(),
					s3UploadService.getFileUrl(file.getFileKey())))
				.toArray(FileInfo[]::new);

			FaqCreateResponse res = FaqCreateResponse.builder()
				.faqCategoryName(faq.getFaqCategory().getCategoryName())
				.title(faq.getTitle())
				.content(faq.getContent())
				.published(faq.getPublished())
				.fileInfos(fileInfos)
				.build();

			return res;
		} catch (Exception e) {
			// 실패 시 업로드된 모든 파일 삭제
			for (UploadFile file : uploadedFiles) {
				s3UploadService.deleteFile(file.getFileKey());
			}
			log.error("Failed to create FAQ", e);
			throw new RuntimeException("FAQ 생성에 실패했습니다", e);
		}
	}
}
