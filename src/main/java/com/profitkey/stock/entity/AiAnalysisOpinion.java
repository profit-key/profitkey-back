package com.profitkey.stock.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "AiAnalysisOpinions")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AiAnalysisOpinion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "AI_REQUEST", columnDefinition = "TEXT", nullable = false)
	private String aiRequest;

	@Column(name = "AI_RESPONSE", columnDefinition = "TEXT", nullable = false)
	private String aiResponse;

	@CreationTimestamp
	@Column(name = "CREATED_AT", updatable = false, nullable = false)
	private LocalDateTime createdAt;

	@ManyToOne()
	@JoinColumn(name = "STOCK_CODE", nullable = false)
	private StockCode stockCode;

	@Builder
	private AiAnalysisOpinion(StockCode stockCode, String aiRequest, String aiResponse) {
		this.stockCode = stockCode;
		this.aiRequest = aiRequest;
		this.aiResponse = aiResponse;
	}
}