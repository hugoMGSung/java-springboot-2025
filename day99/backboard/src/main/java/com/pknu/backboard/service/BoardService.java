package com.pknu.backboard.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.pknu.backboard.entity.Board;
import com.pknu.backboard.repository.BoardRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor 
public class BoardService {

    @Autowired
    private final BoardRepository boardRepository;

    // SELECT * FROM board
    public List<Board> getBoardList() {
        return this.boardRepository.findAll();
    }

    public List<Board> getBoardLists(int page) {
        int start = (page * 10) + 1;
        int end = (page * 10) + 10;
        List<Board> list = this.boardRepository.findBoardPage(start, end);

        return list;
    }

    // 페이징용 게시판 조회메서드
    // 정렬기능 추가 250703. Hugo
    public Page<Board> getBoardList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));  // JPA 클래스와 실제 DB의 컬럼간 이름 비교할것 createDate == create_date. bno를 사용해도 무방
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));  // 10을 변경해서 한페이지에 20, 30개도 표현가능

        return this.boardRepository.findAll(pageable);
    }

    // SELECT * FROM board WHERE bno = ?
    public Board getBoardOne(Long bno) { 
        Optional<Board> opBoard = this.boardRepository.findById(bno);  
        if (opBoard.isPresent()) {
            return opBoard.get();
        } else {
            throw new RuntimeException("board not found");
        }
    }

    // INSERT INTO board VALUES ...
    public void setBoardOne(String title, String content) {
        Board board = new Board();
        board.setTitle(title);  // 파라미터로 넘어온 변수를 파라미터로 입력
        board.setContent(content);  // 내용도 마찬가지
        board.setCreateDate(LocalDateTime.now()); 

        this.boardRepository.save(board);
    }
}
