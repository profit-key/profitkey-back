package com.profitkey.stock.domain.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

@Entity
@Table(name = "UploadFiles")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UploadFile {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "file_name", nullable = false)
	private String fileName;

	@CreationTimestamp
	@Column(name = "created_at", updatable = false, nullable = false)
	private LocalDateTime createdAt;

	@Column(name = "file_key", nullable = false)
	private String fileKey;

	@ManyToOne()
	@JoinColumn(name = "faq_id", nullable = true)
	private Faq faq;

	@ManyToOne()
	@JoinColumn(name = "board_id", nullable = true)
	private Board board;

	@Builder
	private UploadFile(String fileName, String fileKey, Faq faq) {
		this.fileName = fileName;
		this.fileKey = fileKey;
		this.faq = faq;
	}
}

