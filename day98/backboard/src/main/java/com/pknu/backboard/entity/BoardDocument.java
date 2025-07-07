package com.pknu.backboard.entity;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(indexName = "board")
@Getter 
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardDocument {

    @Id
    private Long id;

    private String title;

    @Field(type = FieldType.Text, analyzer = "nori") // 한글 분석기 적용
    private String content;

    public static BoardDocument fromEntity(Board board) {
        return BoardDocument.builder()
                .id(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .build();
    }
}