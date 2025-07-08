package com.pknu.backboard.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

// Java에 동일한 패키지에서 많이 추가되면 *로 생략 가능
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter // 롬복으로 Getter/Setter 자동화
@Entity // JPA 테이블 매핑 선언
public class Board {  

    @Id // PK
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long bno; // Board 테이블의 PK, bno   

    // @Column(name="subject", length = 250)
    @Column(length = 250)
    private String title; // 게시판 제목

    @Column(length = 8000)
    private String content; // 게시글 내용

    // 작성자 추가
    @ManyToOne   // 사용자가 여러개의 글을 작성가능
    private Member writer;

    // 좋아요클릭 사용자 추가
    @ManyToMany
    private Set<Member> like;

    @CreatedDate
    @Column(updatable = false)  // 한번 작성 후 수정하지 않음
    private LocalDateTime createDate; // 게시글 작성일

    @LastModifiedDate
    private LocalDateTime modifyDate; // 게시글 수정일

    // ERD 1:N를 만드는 법
    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<Reply> replyList;
}
