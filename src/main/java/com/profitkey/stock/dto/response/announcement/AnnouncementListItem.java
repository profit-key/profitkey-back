package com.profitkey.stock.dto.response.announcement;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnnouncementListItem {
	private Long id;
	private String title;
	private String createdAt;

	@Builder
	public AnnouncementListItem(Long id, String title, String createdAt) {
		this.id = id;
		this.title = title;
		this.createdAt = createdAt;
	}
}
