package com.pknu.backboard.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.pknu.backboard.entity.Member;
import com.pknu.backboard.repository.MemberRepository;
import com.pknu.backboard.security.MemberRole;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

    @Autowired
    private AuthenticationManager authenticationManager;  // 자동로그인용 계정관리자.

    @Autowired
    private final MemberRepository memberRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    public Member setMember(String username, String email, String password) {
        Member member = new Member();
        member.setUsername(username);
        member.setEmail(email);
        //member.setPassword(password);  // 12345 가 그대로 들어감
        // BCryptPasswordEncoder pwdEnc = new BCryptPasswordEncoder();
        member.setRegDate(LocalDateTime.now());  // 날짜저장 추가
        // SecurityConfig에 새로 선언한 passwordEncoder로 암호화
        member.setPassword(passwordEncoder.encode(password));  // 12345 -> EFWEFER@#$$@#$FEFG#@324423WR 와 같이 암호화
        // 역할 추가
        member.setRole(MemberRole.USER);  // 일반 사용자 추가
        // member.setRole("ROLE_USER");  // 이 방식은 String으로 변경해서 처리해야 함

        this.memberRepository.save(member);
        return member;
    }

    // 같은 이름의 사용자 존재여부 파악 메서드
    public boolean getExistMember(String username) {
        Optional<Member> opMember = this.memberRepository.findByUsername(username);
        if (opMember.isPresent()) {
            return true;  // 같은 아이디 사용자 존재
        } else {
            return false; // 없음. 회원가입 가능
        }
    }

    // 실제 사용자 객체 가져오기
    public Member getMember(String username) {
        Optional<Member> opMember = this.memberRepository.findByUsername(username);
        if (opMember.isPresent()) {
            return opMember.get();   // Member 인스턴스 리턴
        } else {
            throw new RuntimeException("member not found");
        }
    }

    // 자동로그인용 메서드
    @SuppressWarnings("null")
    public void getSignin(String username, String password) {
        // 웹페이지 요청에 대한 FE쪽 객체
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(token);
        // SecurityContextHolder.getContext().setAuthentication(authentication);  // SB 서버 세션에 계정정보 전달
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
        request.getSession(true)
            .setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);
    }
}
