package com.profitkey.stock.domain.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

@Entity
@Table(name = "Faqs")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Faq {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 255)
	private String title;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String content;

	@Column(nullable = false)
	private Boolean published = true;

	@CreationTimestamp
	@Column(name = "created_at", updatable = false, nullable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at", nullable = true)
	private LocalDateTime updatedAt;

	@ManyToOne()
	@JoinColumn(name = "faq_ctg_id", nullable = false)
	private FaqCategoryCode faqCategory;

	@OneToMany(mappedBy = "faq", cascade = CascadeType.ALL)
	private List<UploadFile> uploadFiles;

	@Builder
	public Faq(String title, String content, Boolean published) {
		this.title = title;
		this.content = content;
		this.published = published;
	}
}
