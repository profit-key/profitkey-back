package com.profitkey.stock.dto.common;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class FileInfo {
    private String fileName;
	private String fileUrl;
	private String fileKey;

    @Builder
	public FileInfo(String fileName, String fileKey, String fileUrl) {
        this.fileName = fileName;
        this.fileKey = fileKey;
        this.fileUrl = fileUrl;
    }
}
