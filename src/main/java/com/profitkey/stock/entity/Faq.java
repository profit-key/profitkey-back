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
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "Faqs")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Faq {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String question;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String answer;

	@Column(nullable = false)
	private Boolean published;

	@CreationTimestamp
	@Column(name = "CREATED_AT", updatable = false, nullable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "UPDATED_AT", nullable = true)
	private LocalDateTime updatedAt;

	@Builder
	public Faq(String title, String question, String answer, Boolean published) {
		this.question = question;
		this.answer = answer;
		this.published = published == null ? true : published;
	}
}
