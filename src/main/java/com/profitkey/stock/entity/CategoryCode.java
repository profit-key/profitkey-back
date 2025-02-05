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

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Table(name = "CategoryCodes")
public class CategoryCode {
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

	@Builder
	private CategoryCode(Integer displayOrder, String categoryName, Boolean published) {
		this.displayOrder = displayOrder;
		this.categoryName = categoryName;
		this.published = published;
	}

}
