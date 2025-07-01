package com.pknu.backboard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.pknu.backboard.entity.Board;
import com.pknu.backboard.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/board")  // 공통이 되는 URL 
@Controller
@Log4j2
@RequiredArgsConstructor
public class BoardController {

    @Autowired
    private final BoardService boardService;

    @GetMapping("/list")  // 각 상세 URL만 작성
    public String getList(Model model) {
        List<Board> boardList = this.boardService.getBoardList();

        model.addAttribute("boardList", boardList);
        return "board_list";  // board_list.html 필요
    }
    
    @GetMapping("/detail/{bno}")
    public String getDetail(Model model, @PathVariable("bno") Long bno) {
        Board board = this.boardService.getBoardOne(bno);

        model.addAttribute("board", board);
        return "board_detail"; // board_detail.html 필요
    }
    

}
