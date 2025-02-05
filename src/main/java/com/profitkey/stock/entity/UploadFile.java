package com.profitkey.stock.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "UploadFiles")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UploadFile {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "FILE_NAME", nullable = false)
	private String fileName;

	@CreationTimestamp
	@Column(name = "CREATED_AT", updatable = false, nullable = false)
	private LocalDateTime createdAt;

	@Column(name = "FILE_KEY", nullable = false, unique = true)
	private String fileKey;

	@Builder
	private UploadFile(String fileName, String fileKey, Faq faq) {
		this.fileName = fileName;
		this.fileKey = fileKey;
	}

}

