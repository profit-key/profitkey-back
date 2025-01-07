package com.profitkey.stock.controller;

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

    @Operation(summary = "게시판 목록조회",
        description = """
                        게시판 목록을 목록을 조회한다!!!!!!!
                        """)
    @GetMapping
    public ResponseEntity<List<BoardListResponse>> getBoards() {
        List<BoardListResponse> boards = boardService.getAllBoards();
        log.info("jayce 1111111111111111");
        return ResponseEntity.ok(boards);
    }

    // 게시판 상세조회
    @GetMapping("/{id}")
    public ResponseEntity<BoardDto> getBoardById(@PathVariable Long id) {
        return null;
    }

    // 게시글 작성
    @PostMapping
    public ResponseEntity<BoardDto> createBoard() {
        return null;
    }

    // 게시글 수정
    @PutMapping("/{id}")
    public ResponseEntity<BoardDto> updateBoard(@PathVariable Long id) {
        return null;
    }

    // 게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id) {
        return null;
    }
}
