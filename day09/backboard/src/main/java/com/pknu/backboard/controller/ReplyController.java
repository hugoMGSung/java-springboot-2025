package com.pknu.backboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import com.pknu.backboard.entity.Board;
import com.pknu.backboard.service.BoardService;
import com.pknu.backboard.service.ReplyService;
import com.pknu.backboard.validation.ReplyForm;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@RequestMapping("/reply")
@Controller
@RequiredArgsConstructor
@Log4j2
public class ReplyController {

    @Autowired
    private final BoardService boardService;

    @Autowired
    private final ReplyService replyService;

    // form태그에서 post 액션이 발생하니까. PostMapping을 사용
    @PostMapping("/create/{bno}")
    public String setReply(Model model, @PathVariable("bno") Long bno, 
                           @Valid ReplyForm replyForm, BindingResult bindingResult) {
        Board board = this.boardService.getBoardOne(bno);
        
        if (bindingResult.hasErrors()) {  // 입력검증이 실패하면
            model.addAttribute("board", board);
            return "board/board_detail";
        }
        this.replyService.setReply(board, replyForm.getContent());        
        return String.format("redirect:/board/detail/%s", bno);
    }    
}
