package com.pknu.backboard.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.pknu.backboard.entity.BoardDocument;

@Repository
public interface BoardSearchRepository extends ElasticsearchRepository<BoardDocument, Long> {
    Page<BoardDocument> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);
}
