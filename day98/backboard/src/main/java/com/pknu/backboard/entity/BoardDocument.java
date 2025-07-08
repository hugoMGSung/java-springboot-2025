package com.pknu.backboard.entity;

import java.time.LocalDateTime;

import org.springframework.data.elasticsearch.annotations.Document;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(indexName = "board")  // Elasticsearch에 생성될 인덱스 이름
public class BoardDocument {

    @Id
    private Long bno;

    private String title;

    private String content;

    private String writerName;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    public BoardDocument(Long bno, String title, String content, String writerName, LocalDateTime createDate, LocalDateTime modifyDate) {
        this.bno = bno;
        this.title = title;
        this.content = content;
        this.writerName = writerName;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
    }

    public static BoardDocument from(Board board) {
        return new BoardDocument(
                board.getBno(),
                board.getTitle(),
                board.getContent(),
                board.getWriter() != null ? board.getWriter().getUsername() : null,  // writer 이름 평면화
                board.getCreateDate(),
                board.getModifyDate()
        );
    }
}