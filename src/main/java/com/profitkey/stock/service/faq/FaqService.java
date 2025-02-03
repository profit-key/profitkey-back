package com.profitkey.stock.service.faq;

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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
	public String createFaq(MultipartFile file,Boolean published, String faqCategoryName, String title, String content) {
		try {
			// 카테고리 조회
			FaqCategoryCode category = faqCategoryRepository.findByCategoryName(faqCategoryName)
				.orElseThrow(() -> new RuntimeException("카테고리를 찾을 수 없습니다: " + faqCategoryName));

			// FAQ 생성
			Faq faq = Faq.builder()
				.title(title)
				.content(content)
				.published(published)
				.faqCategory(category)  // 카테고리 설정
				.build();
			
			faqRepository.save(faq);
			
			// 파일이 있는 경우 처리
			if (file != null && !file.isEmpty()) {
				// S3에 파일 업로드
				UploadFile uploadFile = s3UploadService.uploadFile(file);
				
				// UploadFile 엔티티 생성
				UploadFile uploadFileEntity = UploadFile.builder()
					.fileName(uploadFile.getFileName())
					.fileKey(uploadFile.getFileKey())
					.faq(faq)
					.build();
				
				uploadFileRepository.save(uploadFileEntity);
			}
			
			return "FAQ가 생성되었습니다.";
		} catch (IOException e) {
			log.error("Failed to upload file to S3", e);
			throw new RuntimeException("Failed to upload file", e);
		}
	}
}
