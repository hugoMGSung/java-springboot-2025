package com.pknu.backboard.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.pknu.backboard.entity.Board;
import com.pknu.backboard.entity.BoardDocument;
import com.pknu.backboard.entity.Member;
import com.pknu.backboard.repository.BoardRepository;
import com.pknu.backboard.repository.BoardSearchRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor 
public class BoardService {

    @Autowired
    private final BoardRepository boardRepository;

    @Autowired
    private final BoardSearchRepository boardSearchRepository;

    // SELECT * FROM board
    public List<Board> getBoardList() {
        return this.boardRepository.findAll();
    }

    // 페이징용 게시판 조회메서드
    // 정렬기능 추가 250703. Hugo
    // 검색기능 추가, keyword 파라미터 추가 250707. Hugo
    public Page<Board> getBoardList(int page, String keyword) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("bno"));  // JPA 클래스와 실제 DB의 컬럼간 이름 비교할것 createDate == create_date. bno를 사용해도 무방
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));  // 10을 변경해서 한페이지에 20, 30개도 표현가능

        //return this.boardRepository.findAll(pageable);
        return this.boardRepository.findAllByKeyword(keyword, pageable);
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
    public void setBoardOne(String title, String content, Member member) {
        Board board = new Board();
        board.setTitle(title);  // 파라미터로 넘어온 변수를 파라미터로 입력
        board.setContent(content);  // 내용도 마찬가지
        board.setCreateDate(LocalDateTime.now()); 
        // 작성자 추가
        board.setWriter(member);

        this.boardRepository.save(board);
    }

    public Board save(Board board) {
        Board saved = boardRepository.save(board);
        boardSearchRepository.save(BoardDocument.fromEntity(saved));
        return saved;
    }

    public Page<Board> search(String keyword, Pageable pageable) {
        Page<BoardDocument> esResult =
                boardSearchRepository.findByTitleContainingOrContentContaining(keyword, keyword, pageable);

        List<Long> ids = esResult.getContent().stream()
                .map(BoardDocument::getId)
                .collect(Collectors.toList());

        List<Board> boards = boardRepository.findAllById(ids);

        return new PageImpl<>(boards, pageable, esResult.getTotalElements());
    }

    public void putBoardLike(Board board, Member member) {
        board.getLike().add(member);  // 좋아요
        
        this.boardRepository.save(board);  // UPDATE
    }

    // UPDATE board SET ....
    public void putBoardOne(Board board, String title, String content) {
        board.setTitle(title);
        board.setContent(content);
        board.setModifyDate(LocalDateTime.now());

        this.boardRepository.save(board);
    }

    // DELETE FROM board
    public void deleteBoardOne(Board board) {
        this.boardRepository.delete(board);
    }
}
