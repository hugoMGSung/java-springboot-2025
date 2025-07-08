package com.pknu.backboard.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pknu.backboard.entity.BoardDocument;
import com.pknu.backboard.repository.BoardSearchRepository;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class BoardSearchController {

    private final BoardSearchRepository boardSearchRepository;

    @GetMapping
    public Page<BoardDocument> search(
            @RequestParam("keyword") String keyword,
            @PageableDefault(size = 10) Pageable pageable
    ) {
        return boardSearchRepository.findByTitleContainingOrContentContaining(keyword, keyword, pageable);
    }
    
}
