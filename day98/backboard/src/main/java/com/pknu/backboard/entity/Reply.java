package com.pknu.backboard.entity;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Reply {

    @Id // PK -> SpringBoot에서 만드는 Entity는 PK가 없으면 입력이 안됨
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long rno;  // Reply 테이블의 PK, reply-no

    @Column(length = 1000)
    private String content; // 댓글은 제목컬럼이 필요없음
    
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

    // ERD N:1로 관계정립
    @ManyToOne
    private Board board; 
}
