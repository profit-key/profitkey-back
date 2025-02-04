package com.profitkey.stock.service.faq;

import com.profitkey.stock.dto.common.FileInfo;
import com.profitkey.stock.dto.response.faq.FaqCreateResponse;
import com.profitkey.stock.dto.response.faq.FaqListResponse;
import com.profitkey.stock.dto.response.faq.FaqResponse;
import com.profitkey.stock.entity.Faq;
import com.profitkey.stock.entity.UploadFile;
import com.profitkey.stock.repository.UploadFileRepository;
import com.profitkey.stock.repository.faq.FaqRepository;
import com.profitkey.stock.service.S3UploadService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FaqService {
	@Autowired
	private final S3UploadService s3UploadService;
	private final FaqRepository faqRepository;
	private final UploadFileRepository uploadFileRepository;

	@Transactional
	public FaqCreateResponse createFaq(Boolean published, String title, String question, String answer) {
		try {
			// FAQ 생성
			Faq faq = Faq.builder().title(title).question(question).published(published).answer(answer).build();
			faqRepository.save(faq);

			FaqCreateResponse res = FaqCreateResponse.builder().id(faq.getId()).title(faq.getTitle()).question(faq.getQuestion()).published(faq.getPublished()).answer(faq.getAnswer()).build();
			return res;
		} catch (Exception e) {
			log.error("Failed to create FAQ", e);
			throw new RuntimeException("FAQ 생성에 실패했습니다", e);
		}
	}

	@Transactional
	public FaqListResponse getFaqListByCategory() {
		// FAQ 목록 조회
		List<Faq> faqList = faqRepository.findAllOrderByCreatedAtDesc();
		
		// FAQ 목록을 FaqResponse로 변환
		List<FaqResponse> faqResponses = faqList.stream()
			.map(faq -> {
				FileInfo[] fileInfos = faq.getUploadFiles().stream()
					.map(file -> new FileInfo(
						file.getFileName(),
						file.getFileKey(),
						s3UploadService.getFileUrl(file.getFileKey())))
					.toArray(FileInfo[]::new);

				return FaqResponse.builder()
					.id(faq.getId())
					.title(faq.getTitle())
					.question(faq.getQuestion())
					.answer(faq.getAnswer())
					.published(faq.getPublished())
					.createdAt(faq.getCreatedAt())
					.fileInfos(fileInfos)
					.build();
			})
			.collect(Collectors.toList());

		// FaqListResponse 생성 및 반환
		return FaqListResponse.builder()
			.faqList(faqResponses)
			.build();
	}

}
