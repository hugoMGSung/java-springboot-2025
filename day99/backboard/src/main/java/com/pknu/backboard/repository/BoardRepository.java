package com.pknu.backboard.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pknu.backboard.entity.Board;

import lombok.NonNull;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    // 부가적인 기능이 더 필요  
    Board findByTitle(String title);  // 제목으로 검색

    List<Board> findByTitleLike(String title);  // 비슷한 제목으로 검색

    @SuppressWarnings("null")
    @NonNull
    Page<Board> findAll(Pageable pageable); // 페이징 가능한 findAll() 새로 생성

    @Query(value = "SELECT * FROM (SELECT b.*, ROWNUM rnum FROM (SELECT * FROM board ORDER BY create_date DESC) b WHERE ROWNUM <= :end) WHERE rnum >= :start",
           nativeQuery = true)
    List<Board> findBoardPage(@Param("start") int start, @Param("end") int end);
} 
