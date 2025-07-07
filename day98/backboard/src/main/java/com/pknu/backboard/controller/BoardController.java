package com.pknu.backboard.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.pknu.backboard.entity.Board;
import com.pknu.backboard.entity.Member;
import com.pknu.backboard.service.BoardService;
import com.pknu.backboard.service.MemberService;
import com.pknu.backboard.validation.BoardForm;
import com.pknu.backboard.validation.ReplyForm;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.PostMapping;


@RequestMapping("/board")  // 공통이 되는 URL 
@Controller
@Log4j2
@RequiredArgsConstructor
public class BoardController {

    @Autowired
    private final BoardService boardService;

    @Autowired
    private final MemberService memberService;  // 작성자를 위해서 추가

    // @GetMapping("/list")  // 각 상세 URL만 작성
    // public String getList(Model model) {
    //     List<Board> boardList = this.boardService.getBoardList();

    //     model.addAttribute("boardList", boardList);
    //     return "board_list";  // board_list.html 필요
    // }
    @GetMapping("/list")  // 각 상세 URL만 작성
    public String getList(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
                          @RequestParam(value = "keyword", defaultValue = "") String keyword) {
        Page<Board> boardPaging = this.boardService.getBoardList(page, keyword);        
        
        model.addAttribute("boardPaging", boardPaging);
        model.addAttribute("keyword", keyword);
        return "board/board_list";  // board_list.html 필요
    }

    @GetMapping("/search")
    public String search(@RequestParam("keyword") String keyword,
                         @PageableDefault(size = 10) Pageable pageable,
                         Model model) {
        Page<Board> boardPaging = boardService.search(keyword, pageable);
        model.addAttribute("boardPaging", boardPaging);
        model.addAttribute("keyword", keyword);

        return "board/board_list"; // 검색 결과를 보여줄 Thymeleaf 페이지
    }
    
    @GetMapping("/detail/{bno}")
    public String getDetail(Model model, @PathVariable("bno") Long bno, ReplyForm replyForm) {
        Board board = this.boardService.getBoardOne(bno);

        model.addAttribute("board", board);
        return "board/board_detail"; // board_detail.html 필요
    }
    
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")  // 작성을 요청할때 
    public String getCreate(BoardForm boardForm) {
        return "board/board_create";  // board_crate.html 파일 생성
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create") // 저장버튼 클릭 후 
    public String postCreate(@Valid BoardForm boardForm, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "board/board_create";
        }

        // 접속한 사용자 정보 
        Member member = this.memberService.getMember(principal.getName());        
        // 포스트 액션이후 처리
        this.boardService.setBoardOne(boardForm.getTitle(), boardForm.getContent(), member);
        
        return "redirect:/board/list";  
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{bno}")  // 수정 요청
    public String getModify(BoardForm boardForm, @PathVariable("bno") Long bno, Principal principal) {
        Board board = this.boardService.getBoardOne(bno);
        if (!board.getWriter().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다");
        }
        boardForm.setTitle(board.getTitle());
        boardForm.setContent(board.getContent());

        return "board/board_create";
    }
    
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{bno}")  // 수정 요청
    public String postModify(@Valid BoardForm boardForm, BindingResult bindingResult, @PathVariable("bno") Long bno, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "board/board_create";
        }
        Board board = this.boardService.getBoardOne(bno);
        if (!board.getWriter().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다");
        } // 확인사살. 더블체크. 권한이 없으면 수정못하도록 
        
        this.boardService.putBoardOne(board, boardForm.getTitle(), boardForm.getContent());

        return String.format("redirect:/board/detail/%s", bno);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{bno}")
    public String getDelete(@PathVariable("bno") Long bno, Principal principal) {
        Board board = this.boardService.getBoardOne(bno);

        if (!board.getWriter().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다");
        }

        this.boardService.deleteBoardOne(board);

        return "redirect:/board/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/like/{bno}")
    public String getBoardLike(@PathVariable("bno") Long bno, Principal principal) {
        Board board = this.boardService.getBoardOne(bno);
        Member member = this.memberService.getMember(principal.getName()); // 로그인 사용자의 멤버를 가져오기

        this.boardService.putBoardLike(board, member);

        return String.format("redirect:/board/detail/%s", bno);
    }
    
}
