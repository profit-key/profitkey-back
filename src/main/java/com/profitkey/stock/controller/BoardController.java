package com.profitkey.stock.controller;

import com.profitkey.stock.annotation.ApiErrorExceptions;
import com.profitkey.stock.docs.BoardExceptionDocs;
import com.profitkey.stock.docs.SwaggerDocs;
import com.profitkey.stock.domain.dto.BoardDto;
import com.profitkey.stock.domain.response.BoardListResponse;
import com.profitkey.stock.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
@Slf4j
public class BoardController {

    private final BoardService boardService;

    @Operation(summary = SwaggerDocs.SUMMARY_BOARD_LIST,
               description = SwaggerDocs.DESCRIPTION_BOARD_LIST)
    @ApiErrorExceptions(BoardExceptionDocs.class)
    @GetMapping
    public ResponseEntity<List<BoardListResponse>> getBoards() {
        List<BoardListResponse> boards = boardService.getAllBoards();
        return ResponseEntity.ok(boards);
    }

    @Operation(summary = SwaggerDocs.SUMMARY_BOARD_DETAIL,
               description = SwaggerDocs.DESCRIPTION_BOARD_DETAIL)
    @GetMapping("/{id}")
    public ResponseEntity<BoardDto> getBoardById(@PathVariable Long id) {
        return null;
    }

    @Operation(summary = SwaggerDocs.SUMMARY_BOARD_CRAETE,
               description = SwaggerDocs.DESCRIPTION_BOARD_CRAETE)
    @PostMapping
    public ResponseEntity<BoardDto> createBoard() {
        return null;
    }

    @Operation(summary = SwaggerDocs.SUMMARY_BOARD_UPDATE,
               description = SwaggerDocs.DESCRIPTION_BOARD_UPDATE)
    @PutMapping("/{id}")
    public ResponseEntity<BoardDto> updateBoard(@PathVariable Long id) {
        return null;
    }

    @Operation(summary = SwaggerDocs.SUMMARY_BOARD_DELETE,
               description = SwaggerDocs.DESCRIPTION_BOARD_DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id) {
        return null;
    }
}
