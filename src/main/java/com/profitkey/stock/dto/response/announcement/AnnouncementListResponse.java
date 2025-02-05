package com.profitkey.stock.dto.response.announcement;

import com.profitkey.stock.dto.common.Pagenation;
import com.profitkey.stock.entity.Announcement;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "공지사항 목록 조회 응답")
public class AnnouncementListResponse {
	private AnnouncementListItem[] announcements;
	private Pagenation pagenation;

	@Builder
	public AnnouncementListResponse(AnnouncementListItem[] announcements, Pagenation pagenation) {
		this.announcements = announcements;
		this.pagenation = pagenation;
	}

}
