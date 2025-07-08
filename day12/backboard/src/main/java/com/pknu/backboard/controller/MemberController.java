package com.pknu.backboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import com.pknu.backboard.service.MemberService;
import com.pknu.backboard.validation.MemberForm;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private final MemberService memberService;

    @GetMapping("/signin")
    public String getSignIn() {
        return "member/signin";  // signin.html 연결
    }

    @GetMapping("/signup")
    public String getSignUp(MemberForm memberForm) {
        return "member/signup";   // signup.html 생성
    }

    @PostMapping("/signup")
    public String setSignUp(@Valid MemberForm memberForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "member/signup";
        }
        
        // 패스워드와 패스워드 확인이 일치하지 않으면 
        if (!memberForm.getPassword1().equals(memberForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordIncorrect",
                                      "패스워드가 일치하지 않습니다");
            return "member/signup";
        }

        boolean isExist = this.memberService.getExistMember(memberForm.getUsername());
        if (isExist == true) {
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다");
            return "member/signup";
        }

        // 이미 존재하는 유저이름(이메일) 있으면 등록방지
        try {
            // Member 테이블에 저장
            this.memberService.setMember(memberForm.getUsername(), memberForm.getUsername(), memberForm.getPassword1());   
            // 가져온 멤버로 로그인. 최초에 입력된 평문 패스워드 사용            
            this.memberService.getSignin(memberForm.getUsername(), memberForm.getPassword1());
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다");
            return "member/signup";
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "member/signup";
        }

             
        return "redirect:/";  // HOME으로 이동
    }
}
