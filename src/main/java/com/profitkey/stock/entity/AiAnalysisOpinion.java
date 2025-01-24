package com.profitkey.stock.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Builder;

@Entity
@Table(name = "AiAnalysisOpinions")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AiAnalysisOpinion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "ai_request", columnDefinition = "TEXT", nullable = false)
	private String aiRequest;

	@Column(name = "ai_response", columnDefinition = "TEXT", nullable = false)
	private String aiResponse;

	@CreationTimestamp
	@Column(name = "created_at", updatable = false, nullable = false)
	private LocalDateTime createdAt;

	@ManyToOne()
	@JoinColumn(name = "stock_code", nullable = false)
	private StockCode stockCode;

	@Builder
	private AiAnalysisOpinion(StockCode stockCode, String aiRequest, String aiResponse) {
		this.stockCode = stockCode;
		this.aiRequest = aiRequest;
		this.aiResponse = aiResponse;
	}
}