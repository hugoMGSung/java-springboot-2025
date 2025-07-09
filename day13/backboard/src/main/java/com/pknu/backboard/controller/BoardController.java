package com.pknu.backboard.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.multipart.MultipartFile;
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

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;  // C:/websites/upload/

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
    public String postCreate(@Valid BoardForm boardForm, 
                             BindingResult bindingResult, Principal principal, 
                             @RequestParam("file") MultipartFile file) throws IOException {
        if (bindingResult.hasErrors()) {
            return "board/board_create";
        }

        // 접속한 사용자 정보 
        Member member = this.memberService.getMember(principal.getName());
        
        System.out.println(uploadPath);
        File dir = new File(uploadPath);
        if (!dir.exists()) {
            dir.mkdirs(); // 폴더 생성
        }

        if (!file.isEmpty()) {  // 파일 업로드            
            String originalName = file.getOriginalFilename();  // 업로드시는 아무것도 필요없음
            String storedName = UUID.randomUUID().toString() + "_" + originalName;
            String savePath = uploadPath + storedName;

            file.transferTo(new File(savePath));

            this.boardService.setBoardOne(boardForm.getTitle(), boardForm.getContent(), member, 
                                          originalName, storedName, savePath);
        } 
        else {
            // 포스트 액션이후 처리
            this.boardService.setBoardOne(boardForm.getTitle(), boardForm.getContent(), member,
                                          "", "", "");
        }        
        
        
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

        // 파일업로드 필드 추가
        boardForm.setFileOriginalName(board.getFileOriginalName());
        boardForm.setFileStoredName(board.getFileStoredName());
        boardForm.setFilePath(board.getFilePath());        

        return "board/board_create";
    }
    
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{bno}")  // 수정 요청. 파일업로드 파라미터 추가
    public String postModify(@Valid BoardForm boardForm, 
                             BindingResult bindingResult, @PathVariable("bno") Long bno, 
                             Principal principal, @RequestParam("file") MultipartFile file) throws IOException {
        if (bindingResult.hasErrors()) {
            return "board/board_create";
        }
        Board board = this.boardService.getBoardOne(bno);
        if (!board.getWriter().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다");
        } // 확인사살. 더블체크. 권한이 없으면 수정못하도록 

        // 새 파일업로드
        if (!file.isEmpty()) { // 파일 재업로드
            String originalName = file.getOriginalFilename();
            String storedName = UUID.randomUUID().toString() + "_" + originalName;
            String savePath = uploadPath + storedName;

            file.transferTo(new File(savePath));

            // 새파일이 업로드 되었으므로 이전 파일을 전부 교체
            this.boardService.putBoardOne(board, boardForm.getTitle(), boardForm.getContent(), 
                                          originalName, storedName, savePath, true);  // 새파일로 변경되었다고 true 플래그값을 추가
        } else {
            // 이전파일이름 그대로 사용
            this.boardService.putBoardOne(board, boardForm.getTitle(), boardForm.getContent(),
                                          "", "", "", false);
        }

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
    
    @GetMapping("/files/{filename}") 
    public ResponseEntity<Resource> downloadFile(@PathVariable("filename") String filename) throws IOException {
        Path path = Paths.get(uploadPath).resolve(filename);
        Resource resource = new UrlResource(path.toUri());  

        // 파일명을 인코딩후 다운로드
        String encodedFileName = URLEncoder.encode(filename, StandardCharsets.UTF_8).replaceAll("\\+", "%20");
        return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedFileName + "\"")
                    .body(resource);
    }
}
