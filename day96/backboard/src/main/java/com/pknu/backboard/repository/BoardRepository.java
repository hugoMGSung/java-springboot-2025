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

    // 아래의 Query에 적혀있는 쿼리문으로 실행해서 결과를 내겠다는 설명
    // @Query(value = """
    //         SELECT DISTINCT b.*
    //           FROM board b
    //           LEFT JOIN reply r ON r.board_bno = b.bno
    //          WHERE b.title LIKE '%' || :keyword || '%'
    //             OR CONTAINS(b.content, :keyword, 1) > 0
    //             OR r.content LIKE '%' || :keyword || '%'
    //          ORDER BY b.bno DESC
    //         OFFSET :#{#pageable.offset} ROWS FETCH NEXT :#{#pageable.pageSize} ROWS ONLY
    //        """,
    //     countQuery = """
    //         SELECT COUNT(DISTINCT b.bno)
    //           FROM board b
    //           LEFT JOIN reply r ON r.board_bno = b.bno
    //          WHERE b.title LIKE '%' || :keyword || '%'
    //             OR CONTAINS(b.content, :keyword, 1) > 0
    //             OR r.content LIKE '%' || :keyword || '%'
    //         """,
    //     nativeQuery = true)
    // Page<Board> findAllByKeyword(@Param("keyword") String keyword, Pageable pageable); 

    Page<Board> findAllByTitleContainingOrContentContaining(@Param("keyword") String keyword, Pageable pageable);
} 
