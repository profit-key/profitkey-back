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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

@Entity
@Table(name = "FaqCategoryCodes")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FaqCategoryCode {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "display_order")
	private Integer displayOrder;

	@Column(name = "ctg_name", nullable = false, length = 50, unique = true)
	private String categoryName;

	@Column(nullable = false)
	private Boolean published = true;

	@CreationTimestamp
	@Column(name = "created_at", updatable = false, nullable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at", nullable = true)
	private LocalDateTime updatedAt;

	@OneToMany(mappedBy = "id", cascade = CascadeType.ALL)
	private List<Faq> faqs;

	@Builder
	private FaqCategoryCode(Integer displayOrder, String categoryName, Boolean published) {
		this.displayOrder = displayOrder;
		this.categoryName = categoryName;
		this.published = published;
	}
}

