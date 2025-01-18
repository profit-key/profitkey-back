package com.profitkey.stock.controller;

import com.profitkey.stock.domain.response.BoardListResponse;
import com.profitkey.stock.service.BoardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BoardController.class)
class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private BoardService boardService;

    @Test
    public void testGetBoards() throws Exception {
        BoardListResponse board1 = new BoardListResponse();
        board1.setId(1L);
        board1.setTitle("Introduction to Spring Boot");
        board1.setCategoryName("Technology");
        board1.setWriterName("John Doe");
        board1.setCreatedAt(java.time.LocalDateTime.now());

        BoardListResponse board2 = new BoardListResponse();
        board2.setId(2L);
        board2.setTitle("Healthy Living Tips");
        board2.setCategoryName("Health");
        board2.setWriterName("Jane Smith");
        board2.setCreatedAt(java.time.LocalDateTime.now());

        List<BoardListResponse> mockBoards = Arrays.asList(board1, board2);

        when(boardService.getAllBoards()).thenReturn(mockBoards);

        mockMvc.perform(get("/api/board")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].title").value("Introduction to Spring Boot"))
            .andExpect(jsonPath("$[0].categoryName").value("Technology"))
            .andExpect(jsonPath("$[1].id").value(2))
            .andExpect(jsonPath("$[1].title").value("Healthy Living Tips"))
            .andExpect(jsonPath("$[1].categoryName").value("Health"));
    }
}