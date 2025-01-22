package com.profitkey.stock.service;

import com.profitkey.stock.dto.response.BoardListResponse;
import com.profitkey.stock.repository.BoardRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
