package com.profitkey.stock.domain.dto;

import com.profitkey.stock.domain.entity.Board;
import com.profitkey.stock.domain.entity.CategoryCode;

public class BoardDto {
    private Long id;
    private String title;
    private String content;
    private Long writerId;
    private CategoryCode category;

    public static BoardDto fromEntity(Board board) {
        BoardDto dto = new BoardDto();
        dto.id = board.getId();
        dto.title = board.getTitle();
        dto.content = board.getContent();
        return dto;
    }
}
