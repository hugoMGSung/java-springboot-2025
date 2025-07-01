package com.pknu.backboard.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pknu.backboard.entity.Board;
import com.pknu.backboard.entity.Reply;
import com.pknu.backboard.repository.ReplyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReplyService {

    @Autowired
    private final ReplyRepository replyRepository;

    // INSERT INTO reply VALUES (???)
    public void setReply(Board board, String content) {
        Reply reply = new Reply();
        reply.setContent(content);
        reply.setCreateDate(LocalDateTime.now());
        reply.setBoard(board); // 부모테이블 지정. board.bno가 Reply에 저장

        this.replyRepository.save(reply);
    }
}
