package com.pknu.backboard.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pknu.backboard.entity.Board;
import com.pknu.backboard.entity.BoardDocument;
import com.pknu.backboard.repository.BoardRepository;
import com.pknu.backboard.repository.BoardSearchRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardIndexer {

    @Autowired
    private final BoardRepository boardRepository;

    @Autowired
    private final BoardSearchRepository boardSearchRepository; // ★ 이게 누락되었을 가능성 큼

    @PostConstruct
    public void indexAllBoards() {
        List<Board> boards = boardRepository.findAll();

        List<BoardDocument> documents = boards.stream()
                .map(board -> new BoardDocument(
                        board.getBno(),
                        board.getTitle(),
                        board.getContent(),
                        board.getWriter() != null ? board.getWriter().getUsername() : null,
                        board.getCreateDate(),
                        board.getModifyDate()
                ))
                .collect(Collectors.toList());

        boardSearchRepository.saveAll(documents);

        System.out.println("✅ Elasticsearch 색인 완료: " + documents.size() + "건");
    }
}