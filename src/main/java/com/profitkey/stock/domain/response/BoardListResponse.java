package com.profitkey.stock.domain.response;

import com.profitkey.stock.domain.entity.Board;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardListResponse {
    private Long id;
    private String title;
    private String categoryName;
    private String writerName;
    private LocalDateTime createdAt;

    public static BoardListResponse fromEntity(Board board) {
        BoardListResponse response = new BoardListResponse();
        response.id = board.getId();
        response.title = board.getTitle();
        response.categoryName = "profit";
        response.writerName = "jayce";
        response.createdAt = board.getCreatedAt();
        return response;
    }
}
