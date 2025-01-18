package com.profitkey.stock.service;

import com.profitkey.stock.domain.dto.BoardDto;
import com.profitkey.stock.domain.request.board.BoardListRequest;
import com.profitkey.stock.domain.response.BoardListResponse;
import com.profitkey.stock.repository.BoardRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    // 게시판 목록조회
    public List<BoardListResponse> getAllBoards() {
        return boardRepository.findAll()
                                .stream()
                                .map(BoardListResponse::fromEntity)
                                .collect(Collectors.toList());
    }

}
