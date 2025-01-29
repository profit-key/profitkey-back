package com.profitkey.stock.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "Community")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Community {
	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "writer_id")
	private Integer writerId;

	@Column(name = "parent_id")
	private String parentId;

	@Column(name = "content")
	private String content;

	@CreatedDate
	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;
}
